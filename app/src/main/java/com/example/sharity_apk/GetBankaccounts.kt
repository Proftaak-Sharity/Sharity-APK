package com.example.sharity_apk


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.adapter.BankaccountAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.viewmodel.BankaccountViewModel
import com.example.sharity_apk.viewmodel.BankaccountViewModelFactory
import com.example.sharity_apk.databinding.GetBankaccountsBinding
import com.example.sharity_apk.service.SharityApplication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GetBankaccounts: Fragment() {

    private var _binding: GetBankaccountsBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BankaccountViewModel by activityViewModels {
        BankaccountViewModelFactory(
            (activity?.application as SharityApplication).database.bankaccountDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GetBankaccountsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_GetBankaccounts_to_CreateBankaccount)
        }

        recyclerView = binding.myRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        val bankaccountAdapter = BankaccountAdapter {
            val preferences = SharityPreferences(requireContext())
            preferences.setBankaccountId(it.id)
            findNavController().navigate(R.id.action_GetBankaccounts_to_UpdateBankaccount)
        }
        recyclerView.adapter = bankaccountAdapter
        lifecycle.coroutineScope.launch {

            val preferences = SharityPreferences(requireContext())
            val customerNumber = preferences.getCustomerNumber()
            viewModel.getAllBankaccounts(customerNumber).collect(){
                bankaccountAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}