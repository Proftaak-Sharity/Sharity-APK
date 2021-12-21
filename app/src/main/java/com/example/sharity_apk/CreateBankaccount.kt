package com.example.sharity_apk

import android.R.attr
import android.R.attr.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateBankaccountBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import javax.mail.Quota

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import java.lang.Exception


class CreateBankaccount: Fragment() {

    private var _binding: CreateBankaccountBinding? = null
    private val binding get() = _binding!!
//

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateBankaccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val customerNumber = preferences.getCustomerNumber()

        if (customerNumber > 0) {
            binding.btnAddBankaccount.text = getString(R.string.add)
            binding.btnAddBankaccount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_baseline_add_card_24,
                0,
                0,
                0
            )
        } else {
            binding.btnAddBankaccount.text = getString(R.string.start_sharing)
            binding.btnAddBankaccount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_baseline_car_rental_24,
                0,
                0,
                0
            )

        }


        binding.btnAddBankaccount.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {

                if (customerNumber <= 0) {
                    Toast.makeText(
                        requireContext(),
                        "Customer unknown",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val iban = binding.ibanEdittext.text.toString()
                    val accountHolder = binding.accountHolderEdittext.text.toString()

                    try {
                        serviceGenerator.addBankaccount(
                            customerNumber,
                            iban,
                            accountHolder
                        )
                        findNavController().navigate(R.id.action_CreateBankaccount_to_GetBankaccountDetails)
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Bankaccount already in database",
                            Toast.LENGTH_SHORT
                        ).show()
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
