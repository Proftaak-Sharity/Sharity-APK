package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.CreateCustomerBinding

class CreateCustomer : Fragment() {

    private var binding: CreateCustomerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateCustomerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(R.id.action_CreateCustomer_to_CreateDriversLicense)
        }

        binding?.textviewLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_CreateCustomer_to_LoginFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
