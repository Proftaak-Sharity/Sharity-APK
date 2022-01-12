package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateReservationBinding
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CarViewModel
import com.example.sharity_apk.viewmodel.CustomerViewModel
import com.example.sharity_apk.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch

class CreateReservation : Fragment() {

    private var _binding: CreateReservationBinding? = null

    private val reservationViewModel: ReservationViewModel by lazy { ViewModelProvider(this)[ReservationViewModel::class.java] }
    private val customerViewModel: CustomerViewModel by lazy { ViewModelProvider(this)[CustomerViewModel::class.java] }
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = SharityPreferences(requireContext())

        if (preferences.getStartDate().isNullOrEmpty() or preferences.getEndDate()
                .isNullOrEmpty()
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.set_start_end_date),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_CreateReservation_to_SearchCars)
        }

        viewLifecycleOwner.lifecycleScope.launch {

            val car = carViewModel.getCar(preferences.getLicensePlate())
            val owner = customerViewModel.getCustomer(car.customerNumber!!)

            when (val encodedString = carViewModel.getCarImage(car.licensePlate.toString()).image) {
                "1" -> binding.ivCar.setImageResource(R.drawable.volvo_xc90)
                "2" -> binding.ivCar.setImageResource(R.drawable.landrover_defender)
                "3" -> binding.ivCar.setImageResource(R.drawable.tesla_3)
                "4" -> binding.ivCar.setImageResource(R.drawable.ford_mustang_convertible)
                "5" -> binding.ivCar.setImageResource(R.drawable.cupra_leon)
                "6" -> binding.ivCar.setImageResource(R.drawable.mercedes_r350_amg)
                "7" -> binding.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                "8" -> binding.ivCar.setImageResource(R.drawable.opel_vectra)
                "9" -> binding.ivCar.setImageResource(R.drawable.toyota_mirai)
                else -> {
                    binding.ivCar.setImageBitmap(ImageDecoder().decodeImageString(encodedString))
                }
            }
            binding.tvMake.text = car.make
            binding.tvModel.text = car.model
            binding.tvLicensePlate.text = car.licensePlate
            binding.tvAdress.text = owner.address
            binding.tvCity.text = owner.city
            binding.tvPostalCode.text = owner.postalCode
            binding.tvPrice.text = "€ ${"%.2f".format(car.pricePerDay?.toDouble())}"
            binding.tvStartDate.text = preferences.getStartDate()
            binding.tvEndDate.text = preferences.getEndDate()
            val rent = car.pricePerDay
            val pricePerKm = car.pricePerKm
            val kmPackage = binding.txtInputKmPackage.text.toString().toInt()
            preferences.setKmPackage(kmPackage)
            preferences.setPackagePrice((kmPackage.toDouble() * pricePerKm.toDouble()).toString())
            preferences.setRent(rent.toString())

            binding.buttonPayNow.setOnClickListener {

                viewLifecycleOwner.lifecycleScope.launch {

                    preferences.setReservationNumber(addNewReservation("PAID"))
                    findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
                }
            }

            binding.buttonPayLater.setOnClickListener {

                viewLifecycleOwner.lifecycleScope.launch {
                    preferences.setReservationNumber(addNewReservation("OPEN"))
                    findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
                }
            }
        }
    }

    private suspend fun addNewReservation(paymentEnum: String) : Int{
        val preferences = SharityPreferences(requireContext())

        return reservationViewModel.addReservation(
            preferences.getCustomerNumber(),
            preferences.getLicensePlate(),
            preferences.getKmPackage(),
            preferences.getStartDate(),
            preferences.getEndDate(),
            preferences.getRent()?.toDouble(),
            preferences.getPackagePrice()?.toDouble(),
            paymentEnum)
    }
}