package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.GetCustomerDetailsBinding
import com.example.sharity_apk.databinding.UpdateCustomerBinding
import com.example.sharity_apk.viewmodel.CustomerViewModel

class UpdateCustomer: Fragment() {

    private var _binding: UpdateCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      val model: CustomerViewModel by viewModels()
        model.customerResponse.observe(this) {
            binding.result.text = model.customerResponse.value.toString()
        }

//      Button bindings:
        binding.get.setOnClickListener {
            model.customerResponse.value
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


