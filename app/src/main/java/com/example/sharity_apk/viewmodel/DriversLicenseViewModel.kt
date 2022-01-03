package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.DriversLicenseModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator

class DriversLicenseViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)


    suspend fun addDriversLicense(customerNumber: Long, licenseNumber: String, country: String, validUntil: String) {
        serviceGenerator.addDriversLicense(customerNumber, licenseNumber, country, validUntil)
    }


}
