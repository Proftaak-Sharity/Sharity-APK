package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.LoginBinding
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception

class Login : Fragment() {

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = SharityPreferences(requireContext())

        binding.signIn.setOnClickListener { findNavController().navigate(R.id.action_LoginFragment_to_CreateCustomer) }
        binding.buttonLogin.setOnClickListener {

            val emailInput = binding.loginEmailText.text
            val passwordInput = binding.loginPasswordText.text
            val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

            if (emailInput.isNullOrEmpty() || passwordInput.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Email and/or password missing", Toast.LENGTH_SHORT).show()
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val customer =
                        serviceGenerator.getUser(emailInput.toString(), passwordInput.toString())

                        preference.setCustomerNumber(customer.customerNumber!!)
                        preference.setLastName(customer.lastName!!)
                        preference.setFirstName(customer.firstName!!)

                        findNavController().navigate(R.id.action_LoginFragment_to_AccountOverview)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Customer unknown", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}