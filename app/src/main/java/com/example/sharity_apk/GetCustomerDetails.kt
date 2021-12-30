package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.GetCustomerDetailsBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.config.SharityPreferences
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GetCustomerDetails: Fragment() {

    private var _binding: GetCustomerDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

//        Connection textfields to variable
        val lastName: TextView = binding.lastNameDb
        val firstName: TextView = binding.firstNameDb
        val address: TextView = binding.addressDb
        val houseNumber: TextView = binding.houseNumberDb
        val postalCode: TextView = binding.postalCodeDb
        val city: TextView = binding.cityDb
        val country: TextView = binding.countryDb
        val dateOfBirth: TextView = binding.dobDb
        val phoneNumber: TextView = binding.phoneNumberDb
        val email: TextView = binding.emailDb
        val password: TextView = binding.passwordDb

        viewLifecycleOwner.lifecycleScope.launch {

//            connecting customer number from shared preference to variable
            val customerNumber = preferences.getCustomerNumber()

//            using shared preference to retrieve customerdata from api
            val customer = serviceGenerator.getCustomer(customerNumber)

//            connecting customer api-data to textfield
            lastName.text = customer.lastName
            firstName.text = customer.firstName
            address.text = customer.address
            houseNumber.text = customer.houseNumber
            postalCode.text = customer.postalCode
            city.text = customer.city
            country.text = customer.country
            dateOfBirth.text = customer.dateOfBirth
            phoneNumber.text = customer.phoneNumber
            email.text = customer.email
            password.text = customer.password
        }

//      Button bindings:
        binding.buttonEdit.setOnClickListener {findNavController().navigate(R.id.action_GetCustomerDetails_to_UpdateCustomer) }
        binding.buttonBankaccount.setOnClickListener { findNavController().navigate(R.id.action_GetCustomerDetails_to_GetBankaccountDetails) }
        binding.buttonDriversLicense.setOnClickListener {findNavController().navigate(R.id.action_GetCustomerDetails_to_GetDriversLicenseDetails) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

