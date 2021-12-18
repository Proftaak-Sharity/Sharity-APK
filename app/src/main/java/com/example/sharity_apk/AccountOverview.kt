package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.AccountOverviewBinding
import com.example.sharity_apk.model.CustomerModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

class AccountOverview : Fragment() {

    private var _binding: AccountOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AccountOverviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

        val tvTitle: TextView = binding.title
        tvTitle.text = getString(R.string.welcome, "${preferences.getFirstName()}")
//
//      Button bindings:
        binding.buttonMakeReservation.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_SearchCars) }
        binding.buttonMyDetails.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetCustomerDetails) }
        binding.buttonMyCars.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {

                val customerNumber = preferences.getCustomerNumber()
                val driversLicense = serviceGenerator.getDriversLicense(customerNumber)

                Toast.makeText(requireContext(), driversLicense.licenseNumber, Toast.LENGTH_SHORT).show()

            }

        }
        binding.buttonMyReservations.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetAllReservations) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

