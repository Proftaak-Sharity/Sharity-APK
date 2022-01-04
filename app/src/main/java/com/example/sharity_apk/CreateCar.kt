package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCarBinding

class CreateCar : Fragment() {

    private var _binding: CreateCarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateCarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        Implementing Exposed dropdown
        val make = resources.getStringArray(R.array.car_make_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, make)
        binding.evMake.setAdapter(arrayAdapter)

        val preferences = SharityPreferences(requireContext())
        val evAddress = binding.evAddress.text
        val evHouseNumber = binding.evHouseNumber.text
        val evPostalCode = binding.evPostalCode.text
        val evMake = binding.evMake
        val evDateOfBirth = binding.evDob
        val evCity = binding.evCity.text
        val evPhone = binding.evPhone.text


//        OnclickListener on next-button
        binding.buttonSave.setOnClickListener {}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
