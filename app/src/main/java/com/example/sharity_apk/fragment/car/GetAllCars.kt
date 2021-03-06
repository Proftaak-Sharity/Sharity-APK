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
import com.example.sharity_apk.databinding.GetAllCarsBinding
import com.example.sharity_apk.dialog.CreateCarDialog
import com.example.sharity_apk.viewmodel.CarViewModel
import kotlinx.coroutines.launch

class GetAllCars : Fragment(), CarAdapter.OnCarClickListener {

    private var _binding: GetAllCarsBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetAllCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())
            val customerNumber = preferences.getCustomerNumber()

            val carList = carViewModel.getCarsFromCustomer(customerNumber)
            val adapter = CarAdapter(carList, this@GetAllCars)
            try {
                binding.myRecyclerView.adapter = adapter
                binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.myRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAdd.setOnClickListener {
            val dialog = CreateCarDialog()
            dialog.show(parentFragmentManager, "customDialog")
        }
    }

    override fun onItemClick(position: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())
            val customerNumber = preferences.getCustomerNumber()
            val carList = carViewModel.getCarsFromCustomer(customerNumber)
            val clickedCar = carList[position]
            val licensePlate = clickedCar.licensePlate
            preferences.setLicensePlate(licensePlate)

            findNavController().navigate(R.id.action_GetAllCars_to_GetCarDetails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}