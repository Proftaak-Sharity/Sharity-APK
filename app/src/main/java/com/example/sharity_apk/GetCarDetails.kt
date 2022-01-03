package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetCarDetailsBinding
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

class GetCarDetails : Fragment(){

    private var _binding: GetCarDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)

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

//          using shared preference to retrieve customerdata from api
            val car = serviceGenerator.getCar(preferences.getLicensePlate())

//          connecting customer api-data to textfield
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
