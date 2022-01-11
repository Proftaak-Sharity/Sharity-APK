package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCustomerBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_TEXT
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CreateCustomer : Fragment() {

    private var _binding: CreateCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateCustomerBinding.inflate(inflater, container, false)
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
        binding.evDob.setOnClickListener {
            datePicker.show(parentFragmentManager, "")
            datePicker.addOnPositiveButtonClickListener {
                val dateSelected = outputDateFormat.format(it)
                binding.evDob.setText(dateSelected)
            }
        }

        val preferences = SharityPreferences(requireContext())
        val evFirstName = binding.evFirstName.text
        val evLastName = binding.evLastName.text
        val evAddress = binding.evAddress.text
        val evHouseNumber = binding.evHouseNumber.text
        val evPostalCode = binding.evPostalCode.text
        val evCountry = binding.evCountry
        val evDateOfBirth = binding.evDob
        val evCity = binding.evCity.text
        val evPhone = binding.evPhone.text


//        OnclickListener on next-button
        binding.buttonNext.setOnClickListener {

//            Start coroutine
            viewLifecycleOwner.lifecycleScope.launch {
                binding.error.isVisible = false
                try {
                    if (evFirstName.toString().isEmpty() ||
                        evLastName.toString().isEmpty() ||
                        evAddress.toString().isEmpty() ||
                        evHouseNumber.toString().isEmpty() ||
                        evPostalCode.toString().isEmpty() ||
                        evCity.toString().isEmpty() ||
                        evCountry.text.isEmpty() ||
                        evDateOfBirth.text.isNullOrEmpty() ||
                        evPhone.toString().isEmpty()) {

                        binding.error.text = getString(R.string.all_fields_required)
                        binding.error.isVisible = true

                    } else {

//                        set shared prefs until all create account fragments are filled
                        preferences.setFirstName(evFirstName.toString())
                        preferences.setLastName(evLastName.toString())
                        preferences.setAddress(evAddress.toString())
                        preferences.setHouseNumber(evHouseNumber.toString())
                        preferences.setPostalCode(evPostalCode.toString())
                        preferences.setCity(evCity.toString())
                        preferences.setCountry(evCountry.text.toString())
                        preferences.setDateOfBirth(evDateOfBirth.text.toString())
                        preferences.setPhone(evPhone.toString())


                        findNavController().navigate(R.id.action_CreateCustomer_to_CreateDriversLicense)
                    }
                } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_occurred),
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
