package com.example.sharity_apk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import com.example.sharity_apk.data.bankaccount.*
import com.example.sharity_apk.databinding.CreateBankaccountBinding
import com.example.sharity_apk.room.model.BankaccountModel
import com.example.sharity_apk.service.SharityApplication


class CreateBankaccount: Fragment() {

    lateinit var bankaccount: BankaccountModel

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: BankaccountViewModel by activityViewModels {
        BankaccountViewModelFactory(
            (activity?.application as SharityApplication).database.bankaccountDao()
        )
    }

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: CreateBankaccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateBankaccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.ibanEdittext.text.toString(),
            binding.accountHolderEdittext.text.toString()
        )
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.ibanEdittext.text.toString(),
                binding.accountHolderEdittext.text.toString()
            )
            val action = CreateBankaccountDirections.actionCreateBankaccountToGetBankaccountDetails()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddBankaccount.setOnClickListener {
            addNewItem()
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}

//
//    private var _binding: CreateBankaccountBinding? = null
//
//    private lateinit var bankaccount: BankaccountModel
//
//    private val binding get() = _binding!!
//
//    private val viewModel: BankaccountViewModel by lazy {
//        val bankaccountDao = (requireActivity().application as SharityApplication).database.bankaccountDao()
//        BankaccountViewModelFactory(bankaccountDao).create()
//    }
////
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = CreateBankaccountBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnAddBankaccount.setOnClickListener {
//            addBankaccount()
//            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
//        }
//
//
//    }
////
////
////    private fun addBankaccount() {
////            viewModel.addBankaccount(
////                binding.ibanEdittext.text.toString(),
////                binding.accountHolderEdittext.text.toString()
////            )
////        Toast.makeText(requireContext(), binding.ibanEdittext.text.toString(), Toast.LENGTH_LONG).show()
////    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
