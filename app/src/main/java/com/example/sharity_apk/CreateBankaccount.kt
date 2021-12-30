package com.example.sharity_apk

import android.R.attr
import android.R.attr.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateBankaccountBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import javax.mail.Quota

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import java.lang.Exception


class CreateBankaccount: Fragment() {

    private var _binding: CreateBankaccountBinding? = null
    private val binding get() = _binding!!
//

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateBankaccountBinding.inflate(inflater, container, false)
        binding.error.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()

        if (customerNumber > 0) {
            binding.btnAddBankaccount.text = getString(R.string.add)
            binding.btnAddBankaccount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_baseline_add_card_24,
                0,
                0,
                0
            )
        } else {
            binding.btnAddBankaccount.text = getString(R.string.start_sharing)
            binding.btnAddBankaccount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_baseline_car_rental_24,
                0,
                0,
                0
            )
        }

        val etIban = binding.ibanEdittext.text
        val etAccountHolder = binding.accountHolderEdittext.text

        binding.btnAddBankaccount.setOnClickListener {
            binding.error.isVisible = false

            viewLifecycleOwner.lifecycleScope.launch {
                val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

                try {


                    if (etIban.toString().isEmpty() || etAccountHolder.toString().isEmpty()) {
                        binding.error.text = getString(R.string.all_fields_required)
                        binding.error.isVisible = true
                    } else if (customerNumber <= 0) {

                        serviceGenerator.addCustomer(
                            preferences.getFirstName()!!,
                            preferences.getLastName()!!,
                            preferences.getEmail()!!,
                            preferences.getPassword()!!,
                            preferences.getAddress()!!,
                            preferences.getHouseNumber()!!,
                            preferences.getPostalCode()!!,
                            preferences.getCity()!!,
                            preferences.getPhone()!!,
                            preferences.getDateOfBirth()!!,
                            preferences.getCountry()!!
                        )

                        val customer = serviceGenerator.getUser(preferences.getEmail().toString(), preferences.getPassword().toString())

                        serviceGenerator.addDriversLicense(
                            customer.customerNumber,
                            preferences.getLicenseNumber()!!,
                            preferences.getCountryLicense()!!,
                            preferences.getValidUntil()!!
                        )

                        serviceGenerator.addBankaccount(
                            customer.customerNumber,
                            etIban.toString(),
                            etAccountHolder.toString()
                        )
                        preferences.clearPreferences()
                        findNavController().navigate(R.id.action_CreateBankaccount_to_LoginFragment)
                    } else {
                        serviceGenerator.addBankaccount(
                            preferences.getCustomerNumber(),
                            etIban.toString(),
                            etAccountHolder.toString()
                        )
                        findNavController().navigate(R.id.action_CreateBankaccount_to_GetBankaccountDetails)
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
