package com.example.sharity_apk

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateBankaccountBinding
import com.example.sharity_apk.model.BankaccountModel
import com.example.sharity_apk.viewmodel.BankaccountViewModel
import com.example.sharity_apk.viewmodel.BankaccountViewModelFactory
import com.example.sharity_apk.viewmodel.CustomerViewModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.service.SharityApplication
import com.example.sharity_apk.viewmodel.DriversLicenseViewModel
import kotlinx.coroutines.launch


class CreateBankaccount: Fragment() {

    lateinit var bankaccount: BankaccountModel

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val bankaccountViewModel: BankaccountViewModel by activityViewModels {
        BankaccountViewModelFactory(
            (activity?.application as SharityApplication).database.bankaccountDao()
        )
    }

    private val customerViewModel: CustomerViewModel by lazy {
        ViewModelProvider(this)[CustomerViewModel::class.java]
    }

    private val driversLicenseViewModel: DriversLicenseViewModel by lazy {
        ViewModelProvider(this)[DriversLicenseViewModel::class.java]
    }

    // Binding object instance corresponding to the layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: CreateBankaccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateBankaccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()
        val etIban = binding.ibanEdittext.text
        val etAccountHolder = binding.accountHolderEdittext.text

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

            binding.btnAddBankaccount.setOnClickListener {
                binding.error.isVisible = false

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        if (etIban.toString().isEmpty() || etAccountHolder.toString().isEmpty()) {
                            binding.error.text = getString(R.string.all_fields_required)
                            binding.error.isVisible = true

                        } else if (customerNumber <= 0) {
                            addNewCustomer()
                            addNewDriversLicense()
                            addNewBankaccount()

                            preferences.clearPreferences()
                            findNavController().navigate(R.id.action_CreateBankaccount_to_LoginFragment)
                        } else {
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle(getString(R.string.add))
                            builder.setIcon(R.mipmap.ic_launcher)
                            builder.setMessage(getString(R.string.add_bankaccount_dialog))
                            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                                viewLifecycleOwner.lifecycleScope.launch {
                                    addNewBankaccount()
                                    findNavController().navigate(R.id.action_CreateBankaccount_to_GetBankaccounts)
                                }
                            }
                            builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
                            builder.show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    private suspend fun addNewCustomer() {
        val preferences = SharityPreferences(requireContext())
        customerViewModel.addCustomer(
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
    }

    private suspend fun addNewDriversLicense() {
        val preferences = SharityPreferences(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
            val customer = serviceGenerator.getUser(
                preferences.getEmail().toString(),
                preferences.getPassword().toString()
            )
            driversLicenseViewModel.addDriversLicense(
                customer.customerNumber,
                preferences.getLicenseNumber()!!,
                preferences.getCountryLicense()!!,
                preferences.getValidUntil()!!
            )
        }
    }


    private suspend fun addNewBankaccount() {
        val preferences = SharityPreferences(requireContext())

        if (preferences.getCustomerNumber() <= 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
                val customer = serviceGenerator.getUser(
                    preferences.getEmail().toString(),
                    preferences.getPassword().toString())
                bankaccountViewModel.addNewItem(
                    customer.customerNumber,
                    binding.ibanEdittext.text.toString(),
                    binding.accountHolderEdittext.text.toString()
                )
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                bankaccountViewModel.addNewItem(
                    preferences.getCustomerNumber(),
                    binding.ibanEdittext.text.toString(),
                    binding.accountHolderEdittext.text.toString()
                )
            }
        }
    }

        // Called before fragment is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}