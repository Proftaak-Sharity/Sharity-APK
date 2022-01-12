package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.DriversLicenseModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class DriversLicenseViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

    suspend fun getDriversLicense(customerNumber: Long) : DriversLicenseModel {
        return serviceGenerator.getDriversLicense(customerNumber)
    }

    suspend fun checkLicense(licenseNumber: String) :Boolean {
        return serviceGenerator.checkLicense(licenseNumber)
    }

    suspend fun addDriversLicense(customerNumber: Long, licenseNumber: String, country: String, validUntil: String) {
        serviceGenerator.addDriversLicense(customerNumber, licenseNumber, country, validUntil)
    }


}
