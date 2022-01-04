package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCarBinding
import com.example.sharity_apk.dialog.CreateCarDialog
import com.example.sharity_apk.dialog.EmptyFieldDialog
import com.example.sharity_apk.viewmodel.CarViewModel
import kotlinx.coroutines.launch
import java.util.*

class CreateCar : Fragment() {

    private var _binding: CreateCarBinding? = null
    private val binding get() = _binding!!

    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateCarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()
        val carType = preferences.getAddedCarType()



        //        Implementing Exposed dropdown
        val make = resources.getStringArray(R.array.car_make_list)
        val arrayAdapterMake = ArrayAdapter(requireContext(), R.layout.dropdown_item, make)
        binding.etMake.setAdapter(arrayAdapterMake)

        val fueltype = resources.getStringArray(R.array.fueltypes)
        val arrayAdapterFuelType= ArrayAdapter(requireContext(), R.layout.dropdown_item, fueltype)
        binding.etFueltype.setAdapter(arrayAdapterFuelType)

        val etMake = binding.etMake
        val etModel = binding.etModel.text
        val etLicensePlate = binding.etLicensePlate.text
        val etFuelType = binding.etFueltype
        val etCapacity = binding.etCapacity.text
        val etUsage = binding.etUsage.text
        val etPricePerDay = binding.etPricePerDay.text

        when (carType) {
            "Electric" -> {
                binding.title.text = getString(R.string.add_cartype_car, getString(R.string.electric).lowercase(Locale.getDefault()))
                binding.capacityLayout.hint = getString(R.string.battery_capacity_inkW)
                binding.usageLayout.hint = getString(R.string.km_per_kw)
            }
            "Hydrogen" -> {
                binding.title.text = getString(R.string.add_cartype_car, getString(R.string.hydrogen).lowercase(Locale.getDefault()))
                binding.capacityLayout.hint = getString(R.string.size_fueltank)
                binding.usageLayout.hint = getString(R.string.km_per_kilo)
            }
            else -> {
                binding.title.text = getString(R.string.add_cartype_car, getString(R.string.fuel).lowercase(Locale.getDefault()))
                binding.capacityLayout.hint = getString(R.string.size_fueltank)
                binding.usageLayout.hint = getString(R.string.km_per_liter)
                binding.fueltypeLayout.isVisible = true

            }
        }

//        OnclickListener on next-button
        binding.buttonSave.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                when (carType) {
                    "Electric" -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty()) {
                                val dialog = EmptyFieldDialog()
                                dialog.show(parentFragmentManager, "customDialog")
                        } else {
                            carViewModel.addElectricCar(
                                etLicensePlate.toString(),
                                customerNumber,
                                etMake.text.toString(),
                                etModel.toString(),
                                etPricePerDay.toString().toDouble(),
                                etCapacity.toString().toInt(),
                                etUsage.toString().toInt()
                            )
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                    "Hydrogen" -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty()) {
                            val dialog = EmptyFieldDialog()
                            dialog.show(parentFragmentManager, "customDialog")
                        } else {
                            carViewModel.addHydrogenCar(
                                etLicensePlate.toString(),
                                customerNumber,
                                etMake.text.toString(),
                                etModel.toString(),
                                etPricePerDay.toString().toDouble(),
                                etCapacity.toString().toInt(),
                                etUsage.toString().toInt()
                            )
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                    else -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty() ||
                            etFuelType.text.isNullOrEmpty()) {
                                val dialog = EmptyFieldDialog()
                                dialog.show(parentFragmentManager, "customDialog")
                        } else {
                            carViewModel.addFuelCar(
                                etLicensePlate.toString(),
                                customerNumber,
                                etMake.text.toString(),
                                etModel.toString(),
                                etPricePerDay.toString().toDouble(),
                                etCapacity.toString().toInt(),
                                etUsage.toString().toInt(),
                                etFuelType.text.toString()
                            )
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
