package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.LoginBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Login : Fragment() {

    private var _binding: LoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_FirstFragment)
        }

        binding.signIn.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_CreateCustomer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}