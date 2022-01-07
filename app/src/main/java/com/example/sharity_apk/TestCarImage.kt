package com.example.sharity_apk

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Picture
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.sharity_apk.databinding.TestCarImageBinding
import com.example.sharity_apk.viewmodel.CarViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class TestCarImage : Fragment() {

    private var _binding: TestCarImageBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_REQUEST_CODE = 1

    private lateinit var picture: String
    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TestCarImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etLicensePlate = binding.etLicensePlate.text

        binding.button.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                carViewModel.addCarPhoto(etLicensePlate.toString(), picture)


                viewLifecycleOwner.lifecycleScope.launch {
                    val carImage = carViewModel.getCarPhoto(etLicensePlate.toString()).picture

                    binding.ivDatabase.setImageBitmap(decodePicString(carImage))
                }

            }


        }

        binding.btnTakePicture.setOnClickListener {
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
                    val byteArray = byteArrayOutputStream.toByteArray()
                    picture = java.util.Base64.getEncoder().encodeToString(byteArray)

                    binding.ivCamera.load(bitmap) {
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

    fun decodePicString (encodedString: String): Bitmap {

        val imageBytes = java.util.Base64.getDecoder().decode(encodedString)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return decodedImage
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}