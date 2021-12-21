package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.adapter.BankaccountAdapter
import com.example.sharity_apk.adapter.CarAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetBankaccountDetailsBinding
import com.example.sharity_apk.databinding.GetSearchedCarDetailsBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import java.lang.Exception


class GetSearchedCarDetails: Fragment(), CarAdapter.OnCarClickListener {
    private var _binding: GetSearchedCarDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetSearchedCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()

        viewLifecycleOwner.lifecycleScope.launch {

            val car = mutableListOf(serviceGenerator.getCar(customerNumber))

            val adapter = CarAdapter(car, this@GetSearchedCarDetails)

            try {
                binding.RecyclerView.adapter = adapter
                binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
//                binding.RecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}