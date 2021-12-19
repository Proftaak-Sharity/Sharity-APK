package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.databinding.UpdateCustomerBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.adapter.CustomerAdapter
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception


class UpdateCustomer: Fragment() {

    private var _binding: UpdateCustomerBinding? = null
    private val binding get() = _binding!!
    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

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

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val customers = serviceGenerator.getCustomers()
                if (customers.isEmpty()){
                    Toast.makeText(requireContext(), "No customers found", Toast.LENGTH_SHORT).show()
                } else {
                    binding.myRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = CustomerAdapter(customers)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error retrieving data", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}


