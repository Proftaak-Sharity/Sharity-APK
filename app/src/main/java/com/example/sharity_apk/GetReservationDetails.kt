package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetReservationDetailsBinding
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception

class GetReservationDetails: Fragment() {


    private var _binding: GetReservationDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetReservationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("In getreservation details")
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)
        val serviceGenerator2 = ServiceGenerator.buildService(CarApiService::class.java)


//        Connect textfields to variables:
        val reservationStart: TextView = binding.reservationStartDb
        val reservationEnd: TextView = binding.reservationEndDb
        val reservationDate: TextView = binding.reservationDateDb

        val make: TextView = binding.makeDb
        val model: TextView = binding.carModelDb

        val licensePlate: TextView = binding.licensePlateDb
        val kmPackage: TextView = binding.kmPackageDb
        val packagePrice: TextView = binding.kmPackagePriceDb
        val totalPrice: TextView = binding.totalPriceDb
        val paymentStatus: TextView = binding.paymentStatusDb

        viewLifecycleOwner.lifecycleScope.launch {

//            connecting reservation number from shared preference to variable
            val reservationNumber = preferences.getReservationNumber()

//            using shared preference to retrieve reservation data from api
            println("Getting reservation and it is made ?$reservationNumber")
            val reservation = serviceGenerator.getReservation(reservationNumber)
            //      connecting licenseplate from shared preference to variable
            val licensePlateCar = preferences.getReservationLicensePlate()
            val car = serviceGenerator2.getCar(licensePlateCar)

//            connecting reservation api-data to textfield

            reservationStart.text = reservation.startDate
            reservationEnd.text = reservation.endDate
            reservationDate.text = reservation.reservationDate

            make.text = car.make
            model.text = car.model

            licensePlate.text = reservation.licensePlate
            kmPackage.text = reservation.kmPackage.toString()
            packagePrice.text = "€${reservation.packagePrice.toString()}"
            totalPrice.text = "€${reservation.rent.toString()}"
            paymentStatus.text = reservation.paymentEnum

            //Button bindings:
            binding.btnfindCaronMap.setOnClickListener {
                    findNavController().navigate(R.id.action_GetReservationDetails_to_mapsFragment3)
              }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}