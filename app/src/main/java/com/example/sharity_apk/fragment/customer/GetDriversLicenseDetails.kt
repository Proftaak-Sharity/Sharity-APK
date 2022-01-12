package com.example.sharity_apk.fragment.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.R
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetDriversLicenseDetailsBinding
import com.example.sharity_apk.viewmodel.DriversLicenseViewModel
import kotlinx.coroutines.launch

class GetDriversLicenseDetails: Fragment() {

    private var _binding: GetDriversLicenseDetailsBinding? = null
    private val binding get() = _binding!!
    private val driversLicenseViewModel: DriversLicenseViewModel by lazy { ViewModelProvider(this)[DriversLicenseViewModel::class.java] }

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

        val preferences = SharityPreferences(requireContext())

//        Connect texfields to vars
        val licenseNumber: TextView = binding.licenseNumberDb
        val country: TextView = binding.licenseCountryDb
        val validUntil: TextView = binding.validUntilDb

        viewLifecycleOwner.lifecycleScope.launch {

            val customerNumber = preferences.getCustomerNumber()
            val driversLicense = driversLicenseViewModel.getDriversLicense(customerNumber)

            licenseNumber.text = driversLicense.licenseNumber
            country.text = driversLicense.country
            validUntil.text = driversLicense.validUntil
        }

        //      Button bindings:
        binding.buttonPersonal.setOnClickListener { findNavController().navigate(R.id.action_GetDriversLicenseDetails_to_GetCustomerDetails) }
        binding.buttonBankaccount.setOnClickListener { findNavController().navigate(R.id.action_GetDriversLicenseDetails_to_GetBankaccountDetails) }
    }
}