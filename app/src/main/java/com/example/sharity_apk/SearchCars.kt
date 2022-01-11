package com.example.sharity_apk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioButton
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
        val preferences = SharityPreferences(requireContext())

        preferences.setFuelType(getString(R.string.electric))

        // binding the radiogroup to get the fueltype the user wants
        binding.carOptions.setOnCheckedChangeListener { _, i ->
            val radioButton: RadioButton = binding.carOptions.findViewById(i)
            binding.tvOption.text = radioButton.text.toString()
            preferences.setFuelType(radioButton.text.toString())
        }

        //make datepicker buton and bind datpicker
        binding.datePicker.setOnClickListener {
            //make settings go in preference and go to next page
            showDataRangePicker() }

//      Button bindings:
        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_SearchCars_to_SearchResults)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDataRangePicker() {
        val preferences = SharityPreferences(requireContext())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        // use the material datepicker
        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText(getString(R.string.select_date))
                .build()

        dateRangePicker.show(
            parentFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = outputDateFormat.format(dateSelected.first)
            val endDate = outputDateFormat.format(dateSelected.second)

            // show dates on the button
            if (startDate != null && endDate != null) {
                binding.datePicker.text = getString(R.string.rentaldates, startDate, endDate)
            }
            //set the startdate and enddate so the next screens can use this
            preferences.setStartDate(startDate)
            preferences.setEndDate(endDate)
        }
    }


    // convert the time we got from datepicker to a string
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault())
        return format.format(date)
    }
}