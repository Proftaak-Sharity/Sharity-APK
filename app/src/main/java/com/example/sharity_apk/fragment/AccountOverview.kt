package com.example.sharity_apk.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.R
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.AccountOverviewBinding
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CustomerViewModel
import kotlinx.coroutines.launch

class AccountOverview : Fragment() {

    private var _binding: AccountOverviewBinding? = null
    private val binding get() = _binding!!
    private val customerViewModel: CustomerViewModel by lazy { ViewModelProvider(this)[CustomerViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AccountOverviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())
        val customerNumber = preferences.getCustomerNumber()

        binding.title.text = getString(R.string.welcome, "${preferences.getFirstName()}")

        viewLifecycleOwner.lifecycleScope.launch {
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
        }

//      Button bindings:
        binding.buttonMakeReservation.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_SearchCars) }
        binding.buttonMyDetails.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetCustomerDetails) }
        binding.buttonMyCars.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetAllCars) }
        binding.buttonMyReservations.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetAllReservations) }
        binding.ivProfile.setOnClickListener { findNavController().navigate(R.id.action_AccountOverview_to_GetCustomerDetails) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

