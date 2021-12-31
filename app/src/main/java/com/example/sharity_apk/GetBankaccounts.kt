package com.example.sharity_apk


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.adapter.BankaccountAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.viewmodel.BankaccountViewModel
import com.example.sharity_apk.viewmodel.BankaccountViewModelFactory
import com.example.sharity_apk.databinding.GetBankaccountsBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
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
            preferences.setIban(it.iban)
            preferences.setAccountHolder(it.accountHolder)
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


//
//class GetBankaccountDetails: Fragment(), BankaccountAdapter.OnBankaccountClickListener {
//
//    private var _binding: GetBankaccountDetailsBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = GetBankaccountDetailsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
//        val preferences = SharityPreferences(requireContext())
//        val customerNumber = preferences.getCustomerNumber()
//
//        binding.btnAdd.setOnClickListener {
//            Toast.makeText(requireContext(), "$customerNumber", Toast.LENGTH_SHORT).show()
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//
//            val bankaccountsList = serviceGenerator.getBankaccounts(customerNumber)
//            val adapter = BankaccountAdapter(bankaccountsList, this@GetBankaccountDetails)
//            try {
//                binding.myRecyclerView.adapter = adapter
//                binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//                binding.myRecyclerView.setHasFixedSize(true)
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.btnAdd.setOnClickListener {
//            findNavController().navigate(R.id.action_GetBankaccountDetails_to_CreateBankaccount)
//        }
//    }
//
//    override fun onItemClick(position: Int) {
//        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
//        val preferences = SharityPreferences(requireContext())
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            val customerNumber = preferences.getCustomerNumber()
//            val bankaccountsList = serviceGenerator.getBankaccounts(customerNumber)
//            val clickedBankaccount = bankaccountsList[position]
//            val bankaccountId = clickedBankaccount.id
//            preferences.setBankaccount(bankaccountId)
//
//            findNavController().navigate(R.id.action_GetBankaccountDetails_to_UpdateBankaccount)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}