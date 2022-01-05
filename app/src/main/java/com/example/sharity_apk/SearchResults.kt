package com.example.sharity_apk

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.sharity_apk.service.ReservationApiService
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        viewLifecycleOwner.lifecycleScope.launch {

            val alCarList = getCars(fuel)
            val carList = checkAvailability(start, end, alCarList)

            if (carList.isNullOrEmpty()){
                Toast.makeText(requireContext(), "No cars matched your criteria", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SearchResults_to_SearchCars)
            }

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
        val preferences = SharityPreferences(requireContext())
        val start = preferences.getStartDate()
        val end = preferences.getEndDate()
        val fuel = preferences.getFuelType()

        viewLifecycleOwner.lifecycleScope.launch {
            val alCarList = getCars(fuel)
            val carList = checkAvailability(start, end, alCarList)
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


    suspend fun getCars(fuel: String?): MutableList<CarModel> {
        val carServiceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
        println("$fuel is being used in getCars")

        // make this use start/end/fuel if set
        return when (fuel) {
            getString(R.string.petrol) -> {
                carServiceGenerator.getFuelCars()
            }
            getString(R.string.electric) -> {
                carServiceGenerator.getElectricCars()
            }
            getString(R.string.hydrogen) -> {
                carServiceGenerator.getHydrogenCars()
            }
            else -> {
                // search for car in range set
                println("now we use getCars")
                carServiceGenerator.getCars()
            }
        }

    }
}

suspend fun checkAvailability(start: String?, end: String?, carList: MutableList<CarModel>): MutableList<CarModel> {
    val reservationServiceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)
    // check which cars are rented out
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val licensePlates = mutableListOf<String?>()
    val carsToBeRemoved = mutableListOf<CarModel?>()

    if ((start.isNullOrEmpty()) and (end.isNullOrEmpty())){
        return carList
    } else {
        val startDate = LocalDate.parse(start, formatter)
        val endDate = LocalDate.parse(end, formatter)
        try {
            val reserved =
                reservationServiceGenerator.getRentedCars(startDate = startDate, endDate = endDate)
            for (car in reserved) {
                licensePlates += car.licensePlate
            }
            for (car in carList){
                if (car.licensePlate in licensePlates) {
                    carsToBeRemoved += car
                }
            }
            carList.removeAll(carsToBeRemoved.toSet())
        }  catch (e: Exception) {
            return carList
        }
        carList.removeAll(carsToBeRemoved.toSet())
    }
    return carList
}

