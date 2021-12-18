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
import com.example.sharity_apk.model.BankaccountModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import retrofit2.http.POST
import java.text.FieldPosition


class GetBankaccountDetails: Fragment() {

    private var _binding: GetBankaccountDetailsBinding? = null
    private val binding get() = _binding!!
    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

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

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val preferences = SharityPreferences(requireContext())
                val customerNumber = preferences.getCustomerNumber()
                val bankaccounts = serviceGenerator.getBankaccounts(customerNumber)

                if(bankaccounts.isEmpty()) {
                    Toast.makeText(requireContext(), "No bankaccounts found", Toast.LENGTH_SHORT).show()
                } else {
                    binding.myRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = BankaccountAdapter(bankaccounts)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error retrieving data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bankaccountClicked(bankaccountModel : BankaccountModel) {
        Toast.makeText(requireContext(), "Clicked: ${bankaccountModel.iban}", Toast.LENGTH_LONG).show()
    }

    private fun onItemClicked(position: Int) {
        findNavController().navigate(R.id.action_GetBankaccountDetails_to_LoginFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}