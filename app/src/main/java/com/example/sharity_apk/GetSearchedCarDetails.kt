package com.example.sharity_apk

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetSearchedCarDetailsBinding
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CarViewModel
import com.example.sharity_apk.viewmodel.CustomerViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*


class GetSearchedCarDetails: Fragment() {

    private var _binding: GetSearchedCarDetailsBinding? = null
    private val binding get() = _binding!!
    private val customerViewModel: CustomerViewModel by lazy { ViewModelProvider(this)[CustomerViewModel::class.java] }
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }


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

        val preferences = SharityPreferences(requireContext())
        val licensePlate = preferences.getLicensePlate()

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val car = carViewModel.getCar(licensePlate)
                val owner = customerViewModel.getCustomer(car.customerNumber!!)

                when (val encodedString = carViewModel.getCarImage(car.licensePlate.toString()).image) {
                    "1" -> binding.ivCar.setImageResource(R.drawable.volvo_xc90)
                    "2" -> binding.ivCar.setImageResource(R.drawable.landrover_defender)
                    "3" -> binding.ivCar.setImageResource(R.drawable.tesla_3)
                    "4" -> binding.ivCar.setImageResource(R.drawable.ford_mustang_convertible)
                    "5" -> binding.ivCar.setImageResource(R.drawable.cupra_leon)
                    "6" -> binding.ivCar.setImageResource(R.drawable.mercedes_r350_amg)
                    "7" -> binding.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                    "8" -> binding.ivCar.setImageResource(R.drawable.opel_vectra)
                    "9" -> binding.ivCar.setImageResource(R.drawable.toyota_mirai)
                    else -> {
                        val imageCar = ImageDecoder().decodeImageString(encodedString)
                        binding.ivCar.setImageBitmap(imageCar)
                    }
                }

                binding.tvMake.text = car.make
                binding.tvModel.text = car.model
                binding.tvLicensePlate.text = car.licensePlate
                binding.tvAdress.text = owner.address
                binding.tvCity.text = owner.city
                binding.tvPostalCode.text = owner.postalCode
                binding.tvPrice.text = "€ ${"%.2f".format(car.pricePerDay?.toDouble())} ${getString(R.string.per_day)}".also { binding.tvPrice.text = it }
                binding.tvPricePerKm.text = "€ ${"%.2f".format(car.pricePerKm?.toDouble())} ${getString(R.string.per_km)}".also { binding.tvPricePerKm.text = it }
                binding.tvPhone.text = owner.phoneNumber
                binding.tvEmail.text = owner.email
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonMakeReservation.setOnClickListener {
            findNavController().navigate(R.id.action_GetSearchedCarDetails_to_CreateReservation)
        }

        binding.buttonLocate.setOnClickListener {
            findNavController().navigate(R.id.action_GetSearchedCarDetails_to_mapsFragment3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}