package com.example.sharity_apk

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCarBinding
import com.example.sharity_apk.viewmodel.CarViewModel
import java.io.ByteArrayOutputStream
import java.util.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.dialog.EmptyFieldDialog
import kotlinx.coroutines.launch


class CreateCar : Fragment() {

    private var _binding: CreateCarBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

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
            getString(R.string.electric) -> {
                binding.title.text = getString(R.string.add_cartype_car, getString(R.string.electric).lowercase(Locale.getDefault()))
                binding.capacityLayout.hint = getString(R.string.battery_capacity_inkW)
                binding.usageLayout.hint = getString(R.string.km_per_kw)
            }
            getString(R.string.hydrogen) -> {
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

            val encodedString = preferences.getImage()

            viewLifecycleOwner.lifecycleScope.launch {
                when (carType) {
                    getString(R.string.electric) -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty() || encodedString.toString().isEmpty()) {
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
                            carViewModel.addCarImage(etLicensePlate.toString(), encodedString.toString())
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                    getString(R.string.hydrogen) -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty() || encodedString.toString().isEmpty()) {
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
                            carViewModel.addCarImage(etLicensePlate.toString(), encodedString.toString())
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                    else -> {
                        if (etLicensePlate.isNullOrEmpty() || etMake.text.isNullOrEmpty() ||
                            etModel.isNullOrEmpty() || etPricePerDay.isNullOrEmpty() ||
                            etCapacity.isNullOrEmpty() || etUsage.isNullOrEmpty() ||
                            etFuelType.text.isNullOrEmpty() || encodedString.toString().isEmpty()) {
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
                            carViewModel.addCarImage(etLicensePlate.toString(), encodedString.toString())
                            findNavController().navigate(R.id.action_CreateCar_to_GetAllCars)
                        }
                    }
                }
            }
        }

        binding.buttonAddImage.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireContext())
            pictureDialog.setTitle("Select Action")
            val pictureDialogItems = arrayOf("Select photo from Gallery", "Capture photo from Camera")
            pictureDialog.setItems(pictureDialogItems) {
                    _, which ->
                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }
            pictureDialog.show()
        }
    }


    private fun camera()  {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)

    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null
        val preferences = SharityPreferences(requireContext())

        if (requestCode == CAMERA_REQUEST_CODE) {
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imageCar.setImageBitmap(bitmap)

        } else if (requestCode == GALLERY_REQUEST_CODE) {

            val uriDataPath = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uriDataPath)
            binding.imageCar.setImageBitmap(bitmap)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedString = Base64.getEncoder().encodeToString(byteArray)
        preferences.setImage(encodedString)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
