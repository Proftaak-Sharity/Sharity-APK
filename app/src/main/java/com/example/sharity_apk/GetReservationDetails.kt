package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetReservationDetailsBinding
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

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
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)

//        Connect textfields to variables:
        val reservationStart: TextView = binding.reservationStartDb
        val reservationEnd: TextView = binding.reservationEndDb
        val reservationDate: TextView = binding.reservationDateDb

        // TODO import make and model from cars

        val licensePlate: TextView = binding.licensePlateDb
        val kmPackage: TextView = binding.kmPackageDb
        val packagePrice: TextView = binding.kmPackagePriceDb
        val totalPrice: TextView = binding.totalPriceDb
        val paymentStatus: TextView = binding.paymentStatusDb

        viewLifecycleOwner.lifecycleScope.launch {

//            connecting reservation number from shared preference to variable
            val reservationNumber = preferences.getReservationNumber()

//            using shared preference to retrieve reservation data from api
            val reservation = serviceGenerator.getReservation(reservationNumber)

//            connecting reservation api-data to textfield

            reservationStart.text = reservation.startDate
            reservationEnd.text = reservation.endDate
            reservationDate.text = reservation.reservationDate

            // TODO import make and model from cars

            licensePlate.text = reservation.licensePlate
            kmPackage.text = reservation.kmPackage.toString()
            packagePrice.text = reservation.packagePrice.toString()
            totalPrice.text = reservation.rent.toString()
            paymentStatus.text = reservation.paymentEnum


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}