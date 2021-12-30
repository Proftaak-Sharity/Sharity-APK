package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.adapter.CarAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.SearchResultsBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.CustomerApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchResults: Fragment(), CarAdapter.OnCarClickListener {

    private var _binding: SearchResultsBinding? = null
    private val binding get() = _binding!!


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

//        val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
//        val customerServiceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val preferences = SharityPreferences(requireContext())
        val start = preferences.getStartDate()
        val end = preferences.getEndDate()
        val fuel = preferences.getFuelType()
        println(fuel)

        viewLifecycleOwner.lifecycleScope.launch {

            val carList = getCars(start, end, fuel)


            println("Calling adapter now")
            val adapter = CarAdapter(carList, this@SearchResults)
            try {

                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun onItemClick(position: Int) {
        val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
        val preferences = SharityPreferences(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val carList = serviceGenerator.getCars()
            val clickedCar = carList[position]
            val carId = clickedCar.licensePlate.toString()

            preferences.setLicensePlate(carId!!)

            findNavController().navigate(R.id.action_SearchResults_to_GetSearchedCarDetails)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

suspend fun getCars(start: String?, end: String?, fuel: String?): MutableList<CarModel> {
    val carServiceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
//    val customerServiceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
//    var carsToBeRemoved: MutableList<CarModel>? = null

    // make this use start/end/fuel if set
    return if (fuel == "petrol") {
        carServiceGenerator.getFuelCars()
    }else if (fuel == "electric") {
        carServiceGenerator.getElectricCars()
    }else  if (fuel == "hydrogen") {
        carServiceGenerator.getHydrogenCars()
    } else {
        // search for car in range set
        println("now we use getCars")
        carServiceGenerator.getCars()
    }

}