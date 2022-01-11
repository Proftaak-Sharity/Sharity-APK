package com.example.sharity_apk

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.UpdateBankaccountBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.service.SharityApplication
import com.example.sharity_apk.viewmodel.BankaccountViewModel
import com.example.sharity_apk.viewmodel.BankaccountViewModelFactory
import kotlinx.coroutines.launch

class UpdateBankaccount: Fragment() {

    private var _binding: UpdateBankaccountBinding? = null
    private val binding get() = _binding!!

    private val bankaccountViewModel: BankaccountViewModel by activityViewModels {
        BankaccountViewModelFactory(
            (activity?.application as SharityApplication).database.bankaccountDao()
        )
    }

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

        val evIban: TextView = binding.ibanEdittext
        val evAccountHolder: TextView = binding.accountHolderEdittext

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())
            val bankaccount =
                bankaccountViewModel.getBankaccount(preferences.getBankaccountId())

            evIban.text = bankaccount.iban
            evAccountHolder.text = bankaccount.accountHolder

            binding.btnDelete.setOnClickListener {

                try {
                    deleteDialog()
                } catch (e: Exception) {
                    findNavController().navigate(R.id.GetBankaccounts)
                    Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
//
            binding.btnSave.setOnClickListener {
                try {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle(getString(R.string.update))
                    builder.setIcon(R.mipmap.ic_launcher)
                    builder.setMessage(getString(R.string.update_bankaccount_dialog))
                    builder.setPositiveButton(android.R.string.ok) { _, _ ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            bankaccountViewModel.updateBankaccount(
                                preferences.getBankaccountId(),
                                evIban.text.toString(),
                                evAccountHolder.text.toString()
                            )
                            findNavController().navigate(R.id.action_UpdateBankaccount_to_GetBankaccounts)
                        }
                    }
                    builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
                    builder.show()
                } catch (e: Exception) {
                    findNavController().navigate(R.id.GetBankaccounts)
                    Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    private suspend fun addNewBankaccount() {
        val preferences = SharityPreferences(requireContext())

        if (preferences.getCustomerNumber() <= 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
                val customer = serviceGenerator.getUser(
                    preferences.getEmail().toString(),
                    preferences.getPassword().toString())
                bankaccountViewModel.addNewItem(
                    customer.customerNumber,
                    binding.ibanEdittext.text.toString(),
                    binding.accountHolderEdittext.text.toString()
                )
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                bankaccountViewModel.addNewItem(
                    preferences.getCustomerNumber(),
                    binding.ibanEdittext.text.toString(),
                    binding.accountHolderEdittext.text.toString()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteDialog() {
        val preferences = SharityPreferences(requireContext())

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete))
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage(getString(R.string.delete_bankaccount))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                bankaccountViewModel.deleteBankaccount(preferences.getBankaccountId())
                findNavController().navigate(R.id.action_UpdateBankaccount_to_GetBankaccounts)
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }
}