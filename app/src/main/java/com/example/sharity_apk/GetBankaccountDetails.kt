package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.adapter.BankaccountAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetBankaccountDetailsBinding
import com.example.sharity_apk.databinding.GetCustomerDetailsBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception


class GetBankaccountDetails: Fragment(), BankaccountAdapter.OnBankaccountClickListener {

    private var _binding: GetBankaccountDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetBankaccountDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()

        viewLifecycleOwner.lifecycleScope.launch {

            val bankaccountsList = serviceGenerator.getBankaccounts(customerNumber)
            val adapter = BankaccountAdapter(bankaccountsList,this@GetBankaccountDetails)
            try {
                binding.myRecyclerView.adapter = adapter
                binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.myRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {

            }
        }
    }

    override fun onItemClick(position: Int) {
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val preferences = SharityPreferences(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val customerNumber = preferences.getCustomerNumber()
            val bankaccountsList = serviceGenerator.getBankaccounts(customerNumber)
            val clickedBankaccount = bankaccountsList[position]
            val iban = clickedBankaccount.iban.toString()
            preferences.setIban(iban)

            findNavController().navigate(R.id.action_GetBankaccountDetails_to_UpdateBankaccount)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}