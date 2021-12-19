package com.example.sharity_apk

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        val evIban: TextView = binding.ibanEdittext
        val evAccountHolder: TextView = binding.accountHolderEdittext

        viewLifecycleOwner.lifecycleScope.launch {

            val iban = preferences.getIban().toString()
            val bankaccount = serviceGenerator.getBankaccount(iban)

            evIban.text = bankaccount.iban
            evAccountHolder.text = bankaccount.accountHolder


            binding.btnSave.setOnClickListener {

                SAVE FUNCTIE INBOUWEN!!

            }



            binding.btnDelete.setOnClickListener {

                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Remove bankaccount from your account?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            serviceGenerator.deleteBankaccount(iban)
                            findNavController().navigate(R.id.action_UpdateBankaccount_to_GetBankaccountDetails)
                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}