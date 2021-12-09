package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.CreateDriversLicenseBinding

class CreateDriversLicense : Fragment() {

    private var _binding: CreateDriversLicenseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateDriversLicenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//      Button bindings:
        binding.buttonBack.setOnClickListener { findNavController().navigate(R.id.action_CreateDriversLicense_to_CreateCustomer) }
        binding.buttonNext.setOnClickListener { findNavController().navigate(R.id.action_CreateDriversLicense_to_CreateBankaccount) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}