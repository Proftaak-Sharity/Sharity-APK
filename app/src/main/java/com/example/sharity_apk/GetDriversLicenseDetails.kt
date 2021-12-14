package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.GetDriversLicenseDetailsBinding

class GetDriversLicenseDetails: Fragment() {

    private var _binding: GetDriversLicenseDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetDriversLicenseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //      Button bindings:
        binding.buttonEdit.setOnClickListener { findNavController().navigate(R.id.action_GetDriversLicenseDetails_to_UpdateDriversLicense) }
        binding.buttonPersonal.setOnClickListener { findNavController().navigate(R.id.action_GetDriversLicenseDetails_to_GetCustomerDetails) }
        binding.buttonBankaccount.setOnClickListener { findNavController().navigate(R.id.action_GetDriversLicenseDetails_to_GetBankaccountDetails) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}