package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.adapter.ReservationAdapter
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetAllReservationsBinding
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch

class GetAllReservations: Fragment() {

    private var _binding: GetAllReservationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetAllReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                val preferences = SharityPreferences(requireContext())
                val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)

//        Connection textfields to variable
//                val rent: TextView = binding.lastNameDb
//                val reservationnumber: TextView = binding.firstNameDb

                viewLifecycleOwner.lifecycleScope.launch {

//            connecting customer number from shared preference to variable
                    val customerNumber = preferences.getCustomerNumber()

//            using shared preference to retrieve customerdata from api
                    val customer = serviceGenerator.getReservations(customerNumber)

//            connecting customer api-data to textfield
//                    rent.text = reservation.rent
//                    lastName.text = customer.lastName
//                    firstName.text = customer.firstName
//                    address.text = customer.address
//                    houseNumber.text = customer.houseNumber
//                    postalCode.text = customer.postalCode
//                    city.text = customer.city
//                    country.text = customer.country
//                    dateOfBirth.text = customer.dateOfBirth
//                    phoneNumber.text = customer.phoneNumber
//                    email.text = customer.email
//                    password.text = customer.password

                binding.recyclerViewReservation.adapter = ReservationAdapter()
                binding.recyclerViewReservation.setHasFixedSize(true)

}
}
}