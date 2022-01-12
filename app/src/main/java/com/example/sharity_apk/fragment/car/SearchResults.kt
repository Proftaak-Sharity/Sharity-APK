package com.example.sharity_apk.fragment.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.R
import com.example.sharity_apk.adapter.CarAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.SearchResultsBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.viewmodel.CarViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchResults: Fragment(), CarAdapter.OnCarClickListener {

    private var _binding: SearchResultsBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get the dates/fueltype we put in prefs inside searCars.kt
        val preferences = SharityPreferences(requireContext())
        val start = preferences.getStartDate()
        val end = preferences.getEndDate()
        val fuel = preferences.getFuelType()

        viewLifecycleOwner.lifecycleScope.launch {

            //get all cars from fueltype and check if they are available
            val alCarList = getSearchedCars(fuel)
            val carList = carViewModel.checkAvailability(start, end, alCarList)

            if (carList.isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.no_cars_matched), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SearchResults_to_SearchCars)
            }

            val adapter = CarAdapter(carList, this@SearchResults)

            try {
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun getSearchedCars(fuel: String?): MutableList<CarModel> {
        // make this use start/end/fuel if set
        return when (fuel) {
            getString(R.string.petrol)-> { carViewModel.getFuelCars() }
            getString(R.string.electric) -> { carViewModel.getElectricCars() }
            getString(R.string.hydrogen) -> { carViewModel.getHydrogenCars() }
            // search for car in range set
            else -> { carViewModel.getCars() }
        }
    }

    override fun onItemClick(position: Int) {
        val preferences = SharityPreferences(requireContext())
        val start = preferences.getStartDate()
        val end = preferences.getEndDate()
        val fuel = preferences.getFuelType()

        viewLifecycleOwner.lifecycleScope.launch {
            val alCarList = getSearchedCars(fuel)
            val carList = carViewModel.checkAvailability(start, end, alCarList)
            val clickedCar = carList[position]
            val carId = clickedCar.licensePlate.toString()

            preferences.setLicensePlate(carId)

            findNavController().navigate(R.id.action_SearchResults_to_GetSearchedCarDetails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



