package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateDriversLicenseBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CreateDriversLicense : Fragment() {

    private var _binding: CreateDriversLicenseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateDriversLicenseBinding.inflate(inflater, container, false)
        binding.error.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        Implementing Exposed dropdown
        val country = resources.getStringArray(R.array.country_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, country)
        binding.evCountry.setAdapter(arrayAdapter)

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
        binding.evValidUntil.setOnClickListener {
            datePicker.show(parentFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                val dateSelected = outputDateFormat.format(it)
                binding.evValidUntil.setText(dateSelected)
            }
            datePicker.addOnNegativeButtonClickListener {
                datePicker.clearOnCancelListeners()
            }
        }

        val preferences = SharityPreferences(requireContext())
        val evLicenseNumber = binding.licenseNumberEdittext.text
        val evValidUntil = binding.evValidUntil
        val evCountry = binding.evCountry

//      Button bindings:
        binding.buttonNext.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
                binding.error.isVisible = false
                try {

//                Checks if license already connected to other account
                    try {
                        serviceGenerator.checkLicense(evLicenseNumber.toString())
                    } catch (e: Exception) {
                        binding.error.text = getString(R.string.license_already_database)
                        binding.error.isVisible = true
                    }

                    if (evLicenseNumber.toString().isEmpty() ||
                        evValidUntil.text.toString().isEmpty() ||
                        evCountry.text.toString().isEmpty()) {
                        binding.error.text = getString(R.string.all_fields_required)
                        binding.error.isVisible = true
                    } else {
                        preferences.setLicenseNumber(evLicenseNumber.toString())
                        preferences.setValidUntil(evValidUntil.text.toString())
                        preferences.setCountryLicense(evCountry.text.toString())

                        findNavController().navigate(R.id.action_CreateDriversLicense_to_CreateBankaccount)
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}