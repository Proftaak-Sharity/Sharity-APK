package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateReservationBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

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
        val preferences = SharityPreferences(requireContext())
        val carServiceGenerator = ServiceGenerator.buildService(CarApiService::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            val licensePlate = preferences.getLicensePlate()
            val car: CarModel = carServiceGenerator.getCar(licensePlate)
            val selectedCar = car.make + " " +car.model




            if (preferences.getStartDate().isNullOrEmpty() or preferences.getEndDate()
                    .isNullOrEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "To rent a car we need a start and end date",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_CreateReservation_to_GetSearchedCarDetails)
            }

            binding.inputReservationStartDate.text = preferences.getStartDate()
            binding.inputReservationEndDate.text = preferences.getEndDate()
            binding.carSelection.text = selectedCar

        }

        binding.buttonConfirmReservation.setOnClickListener {
            findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
        }

        binding.buttonConfirmReservationPayLater.setOnClickListener {
            findNavController().navigate(R.id.action_CreateReservation_to_GetReservationDetails)
        }
    }
}