package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sharity_apk.adapter.ReservationAdapter
import com.example.sharity_apk.databinding.GetAllReservationsBinding

class GetAllReservations: Fragment() {

    private lateinit var binding: GetAllReservationsBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = GetAllReservationsBinding.inflate(layoutInflater)

            return binding.root
        }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                binding.recyclerViewReservation.adapter = ReservationAdapter()
                binding.recyclerViewReservation.setHasFixedSize(true)

            }

}