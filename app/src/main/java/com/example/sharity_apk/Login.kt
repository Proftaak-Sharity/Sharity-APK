package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

//        Keeps user logged in (check if shared prefs are set)
        if (preference.getCustomerNumber() >= 1) {
            findNavController().navigate(R.id.AccountOverview)
        }

        binding.signIn.setOnClickListener { findNavController().navigate(R.id.action_LoginFragment_to_createAccount) }
        binding.buttonLogin.setOnClickListener {

            binding.error.isVisible = false
            val emailInput = binding.loginEmailText.text
            val passwordInput = binding.loginPasswordText.text
            val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

            if (emailInput.isNullOrEmpty() || passwordInput.isNullOrEmpty()) {
                binding.error.text = getString(R.string.password_email_empty)
                binding.error.isVisible = true
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val customer =
                        serviceGenerator.getUser(emailInput.toString(), passwordInput.toString())

                        preference.setCustomerNumber(customer.customerNumber)
                        preference.setLastName(customer.lastName!!)
                        preference.setFirstName(customer.firstName!!)

                        findNavController().navigate(R.id.action_LoginFragment_to_AccountOverview)
                    } catch (e: Exception) {
                        binding.error.text = getString(R.string.password_email_incorrect)
                        binding.error.isVisible = true
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