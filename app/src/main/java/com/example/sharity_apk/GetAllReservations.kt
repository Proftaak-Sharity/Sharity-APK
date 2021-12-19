package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.adapter.ReservationAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetAllReservationsBinding
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception

class GetAllReservations: Fragment() {

    private var _binding: GetAllReservationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetAllReservationsBinding.inflate(inflater, container, false)

        val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)
        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val reservations = serviceGenerator.getReservations(customerNumber)
                if (reservations.isEmpty()){
                    Toast.makeText(requireContext(), "No reservations found", Toast.LENGTH_LONG).show()
                } else {
                    binding.recyclerViewReservation.apply {
                        layoutManager = LinearLayoutManager(this@GetAllReservations.requireContext())
                        adapter = ReservationAdapter(reservations)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error retrieving data", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
