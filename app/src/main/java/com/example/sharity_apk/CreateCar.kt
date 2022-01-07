package com.example.sharity_apk

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.CreateCarBinding
import com.example.sharity_apk.dialog.EmptyFieldDialog
import com.example.sharity_apk.viewmodel.CarViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream


class CreateCar : Fragment() {

    private var _binding: CreateCarBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_REQUEST_CODE = 1

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

            viewLifecycleOwner.lifecycleScope.launch {
                when (carType) {
                    getString(R.string.electric) -> {
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
                    getString(R.string.hydrogen) -> {
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

        binding.buttonAddImage.setOnClickListener {
            cameraCheckPermission()
        }


    }

    private fun cameraCheckPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRorationalDialogForPermission()
                    }
                }
            ).onSameThread().check()
    }

    private fun camera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val uriSavedImage = Uri.fromFile(File("/Users/robvdhorst/Downloads"))
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {



                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                    binding.imageCar.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                    }
                }
            }
        }
    }

    private fun showRorationalDialogForPermission() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.turned_off_permissions) + "required for this feature. It can be enabled under app settings!!!")
            .setPositiveButton("Go to SETTINGS") {_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
