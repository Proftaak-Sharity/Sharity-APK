package com.example.sharity_apk

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetCarDetailsBinding
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CarViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class GetCarDetails : Fragment(){

    private var _binding: GetCarDetailsBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Connection textfields to variable
        val ivImageCar: ImageView = binding.imageCar
        val tvLicensePlate: TextView = binding.licensePlateDb
        val tvMake: TextView = binding.makeDb
        val tvModel: TextView = binding.modelDb
        val tvFuelType: TextView = binding.fueltypeDb
        val tvCapacity: TextView = binding.capacity
        val tvCapacityDb: TextView = binding.capacityDb
        val tvUsage: TextView = binding.usage
        val tvUsageDb: TextView = binding.usageDb
        val tvRange: TextView = binding.rangeDb
        val tvPricePerDay: TextView = binding.pricePerDayDb
        val tvPricePerKm: TextView = binding.priceKerKmDb

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = SharityPreferences(requireContext())

//          using shared preference to retrieve customerdata from api
            val car = carViewModel.getCar(preferences.getLicensePlate())

            when (val encodedString = carViewModel.getCarImage(car.licensePlate.toString()).image) {
                "1" -> ivImageCar.setImageResource(R.drawable.volvo_xc90)
                "2" -> ivImageCar.setImageResource(R.drawable.landrover_defender)
                "3" -> ivImageCar.setImageResource(R.drawable.tesla_3)
                "4" -> ivImageCar.setImageResource(R.drawable.ford_mustang_convertible)
                "5" -> ivImageCar.setImageResource(R.drawable.cupra_leon)
                "6" -> ivImageCar.setImageResource(R.drawable.mercedes_r350_amg)
                "7" -> ivImageCar.setImageResource(R.drawable.ferrari_testarossa)
                "8" -> ivImageCar.setImageResource(R.drawable.opel_vectra)
                "9" -> ivImageCar.setImageResource(R.drawable.toyota_mirai)
                else -> {
                    val imageCar = ImageDecoder().decodeImageString(encodedString)
                    ivImageCar.setImageBitmap(imageCar)
                }
            }

//          connecting customer api-data to textfield
            tvLicensePlate.text = car.licensePlate
            tvMake.text = car.make
            tvModel.text = car.model
            tvPricePerDay.text = getString(R.string.eur, "%.2f".format(car.pricePerDay?.toDouble()))
            tvPricePerKm.text = getString(R.string.eur, "%.2f".format(car.pricePerKm?.toDouble()))

            when {
                car.batteryCapacity != null -> {
                    tvFuelType.text = getString(R.string.electric)
                    tvCapacity.text = getString(R.string.battery_capacity)
                    tvCapacityDb.text = getString(R.string.kw, car.batteryCapacity)
                    tvUsage.text = getString(R.string.range_per_kw)
                    tvUsageDb.text = getString(R.string.km_per_kw_range, car.kmPerKw)
                    tvRange.text = getString(
                        R.string.km,
                        (car.kmPerKw!!.toInt() * car.batteryCapacity.toInt()).toString()
                    )

                }
                car.kmPerKilo != null -> {
                    tvFuelType.text = getString(R.string.hydrogen)
                    tvCapacity.text = getString(R.string.size_fueltank)
                    tvCapacityDb.text = getString(R.string.kilo, car.sizeFueltank)
                    tvUsage.text = getString(R.string.range_per_kilo)
                    tvUsageDb.text = getString(R.string.km_per_kilo_hydrogen, car.kmPerKilo)
                    tvRange.text = getString(
                        R.string.km,
                        (car.kmPerKilo.toInt() * car.sizeFueltank!!.toInt()).toString()
                    )
                }
                else -> {
                    tvFuelType.text = car.fuelType
                    tvCapacity.text = getString(R.string.size_fueltank)
                    tvCapacityDb.text = getString(R.string.liter, car.sizeFueltank)
                    tvUsage.text = getString(R.string.range_per_liter)
                    tvUsageDb.text = getString(
                        R.string.km_per_liter_fuel, car.kmPerLiterFuel, when (car.fuelType) {
                            "LPG" -> car.fuelType.uppercase(Locale.getDefault())
                            else -> car.fuelType?.lowercase(Locale.getDefault())
                        }
                    )
                    tvRange.text = getString(
                        R.string.km,
                        (car.kmPerLiterFuel!!.toInt() * car.sizeFueltank!!.toInt()).toString()
                    )
                }
            }

//      Button bindings:
            binding.buttonEdit.setOnClickListener {
                findNavController().navigate(R.id.action_GetCarDetails_to_UpdateCar)
            }

            binding.btnDelete.setOnClickListener {

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle(getString(R.string.delete))
                        builder.setIcon(R.mipmap.ic_launcher)
                        builder.setMessage(getString(R.string.delete_car))
                        builder.setPositiveButton(android.R.string.ok) { _, _ ->
                            viewLifecycleOwner.lifecycleScope.launch {
                                carViewModel.deleteCar(
                                    car.licensePlate
                                )
                                findNavController().navigate(R.id.action_GetCarDetails_to_GetAllCars)
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
