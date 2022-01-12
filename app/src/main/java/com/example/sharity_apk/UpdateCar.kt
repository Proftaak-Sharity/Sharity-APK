package com.example.sharity_apk

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.UpdateCarBinding
import com.example.sharity_apk.viewmodel.CarViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class UpdateCar : Fragment(){

    private var _binding: UpdateCarBinding? = null
    private val binding get() = _binding!!

    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Connection textfields to variable
        val licensePlate: TextView = binding.licensePlateDb
        val make: TextView = binding.makeDb
        val model: TextView = binding.modelDb
        val fuelType: TextView = binding.fueltypeDb
        val capacity: TextView = binding.capacity
        val capacityDb: TextView = binding.capacityDb
        val usage: TextView = binding.usage
        val usageDb: TextView = binding.usageDb
        val range: TextView = binding.rangeDb
        val pricePerDay: TextView = binding.etPricePerDay
        val pricePerKm: TextView = binding.priceKerKmDb

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())

//          using shared preference to retrieve customerdata from api
            val car = carViewModel.getCar(preferences.getLicensePlate())

//          connecting customer api-data to textfield
            licensePlate.text = car.licensePlate
            make.text = car.make
            model.text = car.model
            pricePerKm.text = getString(R.string.eur, "%.2f".format(car.pricePerKm.toDouble()))
            pricePerDay.text = "%.2f".format(car.pricePerDay?.toDouble())

            when {
                car.batteryCapacity != null -> {
                    fuelType.text = getString(R.string.electric)
                    capacity.text = getString(R.string.battery_capacity)
                    capacityDb.text = getString(R.string.kw, car.batteryCapacity)
                    usage.text = getString(R.string.range_per_kw)
                    usageDb.text = getString(R.string.km_per_kw_range, car.kmPerKw)
                    range.text = getString(
                        R.string.km,
                        (car.kmPerKw!!.toInt() * car.batteryCapacity.toInt()).toString()
                    )

                }
                car.kmPerKilo != null -> {
                    fuelType.text = getString(R.string.hydrogen)
                    capacity.text = getString(R.string.size_fueltank)
                    capacityDb.text = getString(R.string.kilo, car.sizeFueltank)
                    usage.text = getString(R.string.range_per_kilo)
                    usageDb.text = getString(R.string.km_per_kilo_hydrogen, car.kmPerKilo)
                    range.text = getString(
                        R.string.km,
                        (car.kmPerKilo.toInt() * car.sizeFueltank!!.toInt()).toString()
                    )
                }
                else -> {
                    fuelType.text = car.fuelType
                    capacity.text = getString(R.string.size_fueltank)
                    capacityDb.text = getString(R.string.liter, car.sizeFueltank)
                    usage.text = getString(R.string.range_per_liter)
                    usageDb.text = getString(
                        R.string.km_per_liter_fuel, car.kmPerLiterFuel, when (car.fuelType) {
                            "LPG" -> car.fuelType.uppercase(Locale.getDefault())
                            else -> car.fuelType?.lowercase(Locale.getDefault())
                        }
                    )
                    range.text = getString(
                        R.string.km,
                        (car.kmPerLiterFuel!!.toInt() * car.sizeFueltank!!.toInt()).toString()
                    )
                }
            }

//      Button bindings:
            binding.btnSave.setOnClickListener {

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle(getString(R.string.update))
                        builder.setIcon(R.mipmap.ic_launcher)
                        builder.setMessage(getString(R.string.edit_price))
                        builder.setPositiveButton(android.R.string.ok) { _, _ ->
                            viewLifecycleOwner.lifecycleScope.launch {
                                carViewModel.updateCar(
                                    car.licensePlate,
                                    pricePerDay.text.toString().toDouble()
                                )
                                findNavController().navigate(R.id.action_UpdateCar_to_GetCarDetails)
                            }
                        }
                        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
                        builder.show()
                    } catch (e: Exception) {
                        findNavController().navigate(R.id.GetCarDetails)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_occurred),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
