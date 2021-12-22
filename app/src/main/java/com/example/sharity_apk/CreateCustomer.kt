package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCustomerBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception

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

        val preferences = SharityPreferences(requireContext())
        val evFirstName = binding.evFirstName.text
        val evLastName = binding.evLastName.text
        val evAddress = binding.evAddress.text
        val evHouseNumber = binding.evHouseNumber.text
        val evPostalCode = binding.evPostalCode.text
        val evCity = binding.evCity.text
        val evDateOfBirth = binding.evPostalCode.text
        val evPhone = binding.evPhone.text

//        binding.apply {
//            testBtn.setOnClickListener {
//                // create new instance of DatePickerFragment
//                val datePickerFragment = DatePickerFragment()
//                val supportFragmentManager = requireActivity().supportFragmentManager
//
//                // we have to implement setFragmentResultListener
//                supportFragmentManager.setFragmentResultListener(
//                    "REQUEST_KEY",
//                    viewLifecycleOwner
//                ) { resultKey, bundle ->
//                    if (resultKey == "REQUEST_KEY") {
//                        val date = bundle.getString("SELECTED_DATE")
//                        textViewTest.text = date
//                    }
//                }
//
//                // show
//                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {

//          Button binding:
            binding.buttonNext.setOnClickListener {

                binding.error.isVisible = false
                try {
                    if (evFirstName.toString().isEmpty() ||
                        evLastName.toString().isEmpty() ||
                        evAddress.toString().isEmpty() ||
                        evHouseNumber.toString().isEmpty() ||
                        evPostalCode.toString().isEmpty() ||
                        evCity.toString().isEmpty() ||
                        evDateOfBirth.toString().isEmpty() ||
                        evPhone.toString().isEmpty()) {

                        binding.error.text = getString(R.string.all_fields_required)
                        binding.error.isVisible = true

                    } else {
                        preferences.setFirstName(evFirstName.toString())
                        preferences.setLastName(evLastName.toString())
                        preferences.setAddress(evAddress.toString())
                        preferences.setHouseNumber(evHouseNumber.toString())
                        preferences.setPostalCode(evPostalCode.toString())
                        preferences.setCity(evCity.toString())
                        preferences.setDateOfBirth(evDateOfBirth.toString())
                        preferences.setPhone(evPhone.toString())
                    }
                } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "An error has occurred",
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
