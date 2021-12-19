package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetBankaccountDetailsBinding
import com.example.sharity_apk.databinding.UpdateBankaccountBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

class UpdateBankaccount: Fragment() {

    private var _binding: UpdateBankaccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateBankaccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val tvIban: TextView = binding.ibanDb
        val tvAccountHolder: TextView = binding.bankAccountHolderDb

        viewLifecycleOwner.lifecycleScope.launch {
            val iban = preferences.getIban().toString()
            val bankaccount = serviceGenerator.getBankaccount(iban)

            tvIban.text = bankaccount.iban
            tvAccountHolder.text = bankaccount.accountHolder
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}