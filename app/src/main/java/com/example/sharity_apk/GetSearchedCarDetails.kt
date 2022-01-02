package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.adapter.CarAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetSearchedCarDetailsBinding
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.model.CustomerModel
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

        val carServiceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
        val customerServiceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val preferences = SharityPreferences(requireContext())
        val licensePlate = preferences.getLicensePlate()


        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val car: CarModel = carServiceGenerator.getCar(licensePlate)
                val owner: CustomerModel = customerServiceGenerator.getCustomer(car.customerNumber!!)

                binding.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                binding.tvMake.text = car.make
                binding.tvModel.text = car.model
                binding.tvLicensePlate.text = car.licensePlate
                binding.tvAdress.text = owner.address
                binding.tvCity.text = owner.city
                binding.tvPostalCode.text = owner.postalCode
                binding.tvPrice.text = "Price: " + car.pricePerDay
                binding.tvPricePerKm.text = car.pricePerKm
                binding.tvPhone.text = owner.phoneNumber
                binding.tvEmail.text = owner.email




            } catch (e: Exception) {
                Toast.makeText(requireContext(), "An error has occurred $e", Toast.LENGTH_SHORT).show()
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

    private suspend fun getCustomerCity(customerNumber: Long): String? {
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        return  (serviceGenerator.getCustomer(customerNumber)).city
    }
}