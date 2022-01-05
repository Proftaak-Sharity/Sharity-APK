package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateReservationBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.model.CustomerModel
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.viewmodel.CustomerViewModel
import com.example.sharity_apk.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import java.lang.Exception

class CreateReservation : Fragment() {

    private var _binding: CreateReservationBinding? = null

    private val reservationViewModel: ReservationViewModel by lazy {
        ViewModelProvider(this)[ReservationViewModel::class.java]
    }

    private val carServiceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
    private val customerServiceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CreateReservationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = SharityPreferences(requireContext())
        var retry = false
        println("In Create reservation")
        if (preferences.getStartDate().isNullOrEmpty() or preferences.getEndDate()
                .isNullOrEmpty()
        ) {
            Toast.makeText(
                requireContext(),
                "To rent a car we need a start and end date",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_CreateReservation_to_SearchCars)
        }

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                val car: CarModel = carServiceGenerator.getCar(preferences.getLicensePlate())
                val owner: CustomerModel =
                    customerServiceGenerator.getCustomer(car.customerNumber!!)

                binding.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                binding.tvMake.text = car.make
                binding.tvModel.text = car.model
                binding.tvLicensePlate.text = car.licensePlate
                binding.tvAdress.text = owner.address
                binding.tvCity.text = owner.city
                binding.tvPostalCode.text = owner.postalCode
                binding.tvPrice.text = "Price: " + car.pricePerDay
                binding.tvStartDate.text = preferences.getStartDate()
                binding.tvEndDate.text = preferences.getEndDate()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "An error has occurred $e", Toast.LENGTH_SHORT)
                    .show()
            }
            println("Make reservation Create reservation")

            binding.buttonPayNow.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val car: CarModel =
                            carServiceGenerator.getCar(preferences.getLicensePlate())
                        // here we bind kmpackage, rent, packagePrice

                        val rent = car.pricePerDay
                        val packagePrice = car.pricePerKm
                        val kmPackage = binding.txtInputKmPackage.text
                        val kmPackageInt = kmPackage.toString().toInt()
                        println("$rent rent $packagePrice packagePrice $kmPackage kmpackage ")

                        preferences.setKmPackage(kmPackageInt)
                        preferences.setPackagePrice("$packagePrice")
                        preferences.setRent("$rent")

                        val reservationNumber = addNewReservation("PAID")
                        println(reservationNumber)
                        preferences.setReservationNumber(reservationNumber)

                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong, the car might already be rented out by now",
                            Toast.LENGTH_SHORT
                        ).show()
                        var retry = true
                    }

                    // if something goes wrong go back to searchcars
                    if (retry) {
                        findNavController().navigate(R.id.action_CreateReservation_to_SearchCars)
                    } else {
                        findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
                    }


                }

            }

            binding.buttonPayLater.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val car: CarModel =
                            carServiceGenerator.getCar(preferences.getLicensePlate())
                        // here we bind kmpackage, rent, packagePrice
                        val rent = car.pricePerDay
                        val packagePrice = car.pricePerKm!!
                        val kmPackage = binding.txtInputKmPackage.text
                        val kmPackageInt = kmPackage.toString().toInt()
                        println("$rent rent $packagePrice packagePrice $kmPackage kmpackage ")

                        preferences.setKmPackage(kmPackageInt)
                        preferences.setPackagePrice(packagePrice)
                        preferences.setRent("$rent")

                        val reservationNumber = addNewReservation("OPEN")
                        preferences.setReservationNumber(reservationNumber)

                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong, the car might already be rented out by now",
                            Toast.LENGTH_SHORT
                        ).show()
                        var retry = true
                    }

                    // if something goes wrong go back to searchcars
                    if (retry) {
                        findNavController().navigate(R.id.action_CreateReservation_to_SearchCars)
                    } else {
                        findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
                    }
                }
            }
        }
    }

    private suspend fun addNewReservation(paymentEnum: String): Int {
        val preferences = SharityPreferences(requireContext())
        println("In addNewReservation")
        return reservationViewModel.addReservation(
            preferences.getCustomerNumber(),
            preferences.getLicensePlate(),
            preferences.getKmPackage(),
            preferences.getStartDate(),
            preferences.getEndDate(),
            preferences.getRent()?.toDouble(),
            preferences.getPackagePrice()?.toDouble(),
            paymentEnum
        )
    }
}