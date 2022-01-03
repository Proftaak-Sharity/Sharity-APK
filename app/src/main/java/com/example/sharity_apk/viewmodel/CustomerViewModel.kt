package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator

class CustomerViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)


    suspend fun addCustomer(firstName: String,
                            lastName: String,
                            email: String?,
                            password: String?,
                            address: String?,
                            houseNumber: String?,
                            postalCode: String,
                            city: String,
                            phoneNumber: String,
                            dateOfBirth: String,
                            country: String) {

        serviceGenerator.addCustomer(firstName, lastName, email, password, address, houseNumber, postalCode, city, phoneNumber, dateOfBirth, country)
    }
}
