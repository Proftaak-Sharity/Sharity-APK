package com.example.sharity_apk

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.UpdateCustomerBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class UpdateCustomer: Fragment() {

    private var _binding: UpdateCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val preferences = SharityPreferences(requireContext())




//        Building Material DatePicker
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.valid_until))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

//        OnClickListener for Date of Birth
        binding.evDob.setOnClickListener {
            datePicker.show(parentFragmentManager, "")
            datePicker.addOnPositiveButtonClickListener {
                val dateSelected = outputDateFormat.format(it)
                binding.evDob.setText(dateSelected)
            }
        }

        val evFirstName: TextView = binding.evFirstName
        val evLastName: TextView = binding.evLastName
        val evAddress: TextView = binding.evAddress
        val evHouseNumber: TextView = binding.evHouseNumber
        val evPostalCode: TextView = binding.evPostalCode
        val evCountry: TextView = binding.evCountry
        val evDateOfBirth: TextView = binding.evDob
        val evCity: TextView = binding.evCity
        val evPhone: TextView = binding.evPhone



        viewLifecycleOwner.lifecycleScope.launch {

            val customer = serviceGenerator.getCustomer(preferences.getCustomerNumber())


            evFirstName.text = customer.firstName
            evLastName.text = customer.lastName
            evAddress.text = customer.address
            evHouseNumber.text = customer.houseNumber
            evPostalCode.text = customer.postalCode
            evCountry.text = customer.country
            evDateOfBirth.text = customer.dateOfBirth
            evCity.text = customer.city
            evPhone.text = customer.phoneNumber

            //        Implementing Exposed dropdown
            val country = resources.getStringArray(R.array.country_list)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, country)
            binding.evCountry.setAdapter(arrayAdapter)
        }

//        OnclickListener on save-button
        binding.buttonSave.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {

                val customer = serviceGenerator.getCustomer(preferences.getCustomerNumber())
                Toast.makeText(requireContext(), evCountry.text.toString(), Toast.LENGTH_SHORT).show()

                try {
                    val builder = AlertDialog.Builder(requireContext())
                    val negativeButton = builder.setMessage(getString(R.string.change_account))
                        .setCancelable(false)
                        .setPositiveButton("Yes") { _, _ ->
                            viewLifecycleOwner.lifecycleScope.launch {
                                
                                serviceGenerator.updateCustomer(
                                    customer.customerNumber!!,
                                    evFirstName.text.toString(),
                                    evLastName.text.toString(),
                                    evAddress.text.toString(),
                                    evHouseNumber.text.toString(),
                                    evPostalCode.text.toString(),
                                    evCity.text.toString(),
                                    evCountry.text.toString(),
                                    evDateOfBirth.text.toString(),
                                    evPhone.text.toString())
                                findNavController().navigate(R.id.action_UpdateCustomer_to_GetCustomerDetails)
                            }
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                    val alert = builder.create()
                    alert.show()
                } catch (e: Exception) {
                    findNavController().navigate(R.id.GetCustomerDetails)
                    Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


