package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.SearchCarsBinding

class SearchCars : Fragment() {

    private var _binding: SearchCarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchCarsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//      Button bindings:
        binding.searchButton.setOnClickListener { findNavController().navigate(R.id.action_SearchCars_to_SearchResults) }
        binding.backButton.setOnClickListener { findNavController().navigate(R.id.action_SearchCars_to_AccountOverview) }
//        binding.buttonMyDetails.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_LoginFragment) }
//        binding.buttonMyCars.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_LoginFragment) }
//        binding.buttonMyReservations.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_LoginFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}