package com.example.sharity_apk.fragment.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.R
import com.example.sharity_apk.adapter.ReservationAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetAllReservationsBinding
import com.example.sharity_apk.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class GetAllReservations: Fragment(), ReservationAdapter.OnReservationClickListener {

    private var _binding: GetAllReservationsBinding? = null
    private val binding get() = _binding!!

    private val reservationViewModel: ReservationViewModel by lazy { ViewModelProvider(this)[ReservationViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetAllReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())
            val customerNumber = preferences.getCustomerNumber()

            val reservationList = reservationViewModel.getReservations(customerNumber)
            val adapter = ReservationAdapter(reservationList, this@GetAllReservations)

            try {
                binding.recyclerViewReservation.adapter = adapter
                binding.recyclerViewReservation.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerViewReservation.setHasFixedSize(true)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val preferences = SharityPreferences(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val customerNumber = preferences.getCustomerNumber()
            val reservationList = reservationViewModel.getReservations(customerNumber)
            val clickedReservation = reservationList[position]
            val reservationNumber = clickedReservation.reservationNumber
            preferences.setReservationNumber(reservationNumber)
            val licensePlate = clickedReservation.licensePlate
            if (licensePlate != null) {
                preferences.setLicensePlate(licensePlate)
            }

            findNavController().navigate(R.id.action_GetAllReservations_to_GetReservationDetails)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
