package com.example.sharity_apk.fragment.customer

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.R
import com.example.sharity_apk.databinding.GetCustomerDetailsBinding
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CustomerViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

class GetCustomerDetails: Fragment() {

    private var _binding: GetCustomerDetailsBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    private val customerViewModel: CustomerViewModel by lazy {
        ViewModelProvider(this)[CustomerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())


//        Connection textfields to variable
        val lastName: TextView = binding.lastNameDb
        val firstName: TextView = binding.firstNameDb
        val address: TextView = binding.addressDb
        val houseNumber: TextView = binding.houseNumberDb
        val postalCode: TextView = binding.postalCodeDb
        val city: TextView = binding.cityDb
        val country: TextView = binding.countryDb
        val dateOfBirth: TextView = binding.dobDb
        val phoneNumber: TextView = binding.phoneNumberDb
        val email: TextView = binding.emailDb
        val password: TextView = binding.passwordDb
        val balance: TextView = binding.balanceDb

        viewLifecycleOwner.lifecycleScope.launch {

//            connecting customer number from shared preference to variable
            val customerNumber = preferences.getCustomerNumber()

//            using shared preference to retrieve customerdata from api
            val customer = customerViewModel.getCustomer(customerNumber)

            try {
                when (val encodedString = customerViewModel.getCustomerImage(customerNumber).image) {
                    "1" -> binding.ivProfile.setImageResource(R.drawable.customer_1)
                    "2" -> binding.ivProfile.setImageResource(R.drawable.customer_2)
                    "3" -> binding.ivProfile.setImageResource(R.drawable.customer_3)
                    "4" -> binding.ivProfile.setImageResource(R.drawable.customer_4)
                    "5" -> binding.ivProfile.setImageResource(R.drawable.customer_5)
                    "6" -> binding.ivProfile.setImageResource(R.drawable.lionel_messi)
                    else -> binding.ivProfile.setImageBitmap(ImageDecoder().decodeImageString(encodedString))
                }
            } catch (e: Exception) {
                binding.ivProfile.setImageResource(R.drawable.no_profile_image)
            }

//            connecting customer api-data to textfield
            lastName.text = customer.lastName
            firstName.text = customer.firstName
            address.text = customer.address
            houseNumber.text = customer.houseNumber
            postalCode.text = customer.postalCode
            city.text = customer.city
            country.text = customer.country
            dateOfBirth.text = customer.dateOfBirth
            phoneNumber.text = customer.phoneNumber
            email.text = customer.email
            password.text = customer.password
            balance.text = "€ ${"%.2f".format(customer.balance)}"
        }

//      Button bindings:
        binding.buttonEdit.setOnClickListener {findNavController().navigate(R.id.action_GetCustomerDetails_to_UpdateCustomer) }
        binding.buttonBankaccount.setOnClickListener { findNavController().navigate(R.id.action_GetCustomerDetails_to_GetBankaccounts) }
        binding.buttonDriversLicense.setOnClickListener {findNavController().navigate(R.id.action_GetCustomerDetails_to_GetDriversLicenseDetails) }
        binding.btnChangeProfile.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireContext())
            pictureDialog.setTitle(getString(R.string.select_action))
            val pictureDialogItems = arrayOf(getString(R.string.photo_from_gallery), getString(R.string.photo_from_camera))
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
            binding.ivProfile.setImageBitmap(bitmap)

        } else if (requestCode == GALLERY_REQUEST_CODE) {

            val uriDataPath = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uriDataPath)
            binding.ivProfile.setImageBitmap(bitmap)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedString = Base64.getEncoder().encodeToString(byteArray)
        viewLifecycleOwner.lifecycleScope.launch {
            customerViewModel.addCustomerImage(preferences.getCustomerNumber(), encodedString)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

