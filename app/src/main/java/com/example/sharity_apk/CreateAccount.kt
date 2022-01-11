package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateAccountBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateAccount : Fragment() {

    private var _binding: CreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateAccountBinding.inflate(inflater, container, false)

        binding.emailError.isVisible = false
        binding.passwordError.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        preferences.clearPreferences()
        val tvPassword = binding.passwordEdittext.text
        val tvEmail = binding.emailEdittext.text
        val tvPasswordConfirm = binding.passwordConfirmEdittext.text

        binding.buttonNext.setOnClickListener {

            binding.emailError.isVisible = false
            binding.passwordError.isVisible = false
            binding.passwordCharError.isVisible = false

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
                    val checkedEmail = serviceGenerator.checkEmail(tvEmail.toString())

//                    check if fields are filled
                    if (tvEmail.toString().isEmpty()) {
                        binding.emailError.text = getString(R.string.email_empty)
                        binding.emailError.isVisible = true
                    } else if (checkedEmail) {
                        binding.emailError.text = getString(R.string.email_already_in_db)
                        binding.emailError.isVisible = true
                    } else if (tvPassword.toString().isEmpty() || tvPasswordConfirm.toString().isEmpty()) {
                        binding.passwordError.text = getString(R.string.password_empty)
                        binding.passwordError.isVisible = true

//                      check if passwords are same
                    } else if (tvPassword.toString() != tvPasswordConfirm.toString()) {
                        binding.passwordError.text = getString(R.string.passwords_not_matching)
                        binding.passwordError.isVisible = true

//                      check if passwords are more then 8 chars
                    } else if (tvPassword.toString().length < 8) {
                        binding.passwordCharError.text = getString(R.string.password_char_error)
                        binding.passwordCharError.isVisible = true

//                      set shared prefs
                    } else {
                        preferences.setEmail(tvEmail.toString())
                        preferences.setPassword(tvPassword.toString())
                        findNavController().navigate(R.id.action_CreateAccount_to_CreateCustomer)
                    }

//                     catch exception if email not meet requirements like @ and .
                } catch (e: Exception) {
                    binding.emailError.text = getString(R.string.email_regex_error)
                    binding.emailError.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
