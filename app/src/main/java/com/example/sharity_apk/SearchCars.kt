package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
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
        binding.searchButton.setOnClickListener {
            //get whats in city and put in pref
            var city = binding.citySearch.text
            var cityString = city.toString()




            val preferences = SharityPreferences(requireContext())
            preferences.setCity(cityString)
            findNavController().navigate(R.id.action_SearchCars_to_SearchResults) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}