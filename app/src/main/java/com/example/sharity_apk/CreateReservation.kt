package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.CreateReservationBinding

class CreateReservation : Fragment() {

    private var _binding: CreateReservationBinding? = null

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

        binding.buttonConfirmReservation.setOnClickListener {
            findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
        }

        binding.buttonConfirmReservationPayLater.setOnClickListener {
            findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
        }
    }
}