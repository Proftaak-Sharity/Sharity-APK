package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.LoginBinding
import com.example.sharity_apk.databinding.SearchCarsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioButton

import android.widget.RadioGroup
import android.widget.Toast

import android.R
import androidx.databinding.DataBindingUtil.setContentView


class SearchCars : Fragment() {

    private var _binding: SearchCarsBinding? = null
    private val binding get() = _binding!!
    private var radioGroup: RadioGroup? = null
    private var radioButton: RadioButton? = null
    private var btnDisplay: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = SharityPreferences(requireContext())

            binding.carOptions.setOnCheckedChangeListener { radioGroup, i ->
                var radioButton: RadioButton? = binding.carOptions.findViewById<RadioButton>(i)
                if (radioButton != null) {
                    binding.tvOption.text = radioButton.text.toString()
                }
                preferences.setFuelType(radioButton?.text.toString())
        }

        //make datepicker buton and bind datpicker
        binding.datePicker.setOnClickListener {
            //make settings go in preference and go to next page
            showDataRangePicker() }




//      Button bindings:
        binding.searchButton.setOnClickListener {
            //get whats in city and put in pref
            findNavController().navigate(com.example.sharity_apk.R.id.action_SearchCars_to_SearchResults)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDataRangePicker() {
        val preferences = SharityPreferences(requireContext())

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            parentFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = dateSelected.first
            val endDate = dateSelected.second

            if (startDate != null && endDate != null) {
                binding.datePicker.text =
                    "StartDate: ${convertLongToTime(startDate)}\n" +
                            "EndDate: ${convertLongToTime(endDate)}"
            }
            preferences.setStartDate(startDate)
            preferences.setEndDate(startDate)

        }

    }





    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault())
        return format.format(date)
    }

}