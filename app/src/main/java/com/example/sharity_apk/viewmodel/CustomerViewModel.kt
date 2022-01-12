package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.CustomerImageModel
import com.example.sharity_apk.model.CustomerModel
import com.example.sharity_apk.model.LoginModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import retrofit2.http.*

class CustomerViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

    suspend fun getCustomer(customerNumber: Long) : CustomerModel {
        return serviceGenerator.getCustomer(customerNumber)
    }

    suspend fun checkEmail(email: String) :Boolean {
        return serviceGenerator.checkEmail(email)
    }

    suspend fun getUser(email: String, password: String) : LoginModel {
        return serviceGenerator.getUser(email, password)
    }

    suspend fun addCustomer(firstName: String, lastName: String, email: String?, password: String?, address: String?, houseNumber: String?, postalCode: String, city: String, phoneNumber: String, dateOfBirth: String, country: String) {
        serviceGenerator.addCustomer(firstName, lastName, email, password, address, houseNumber, postalCode, city, phoneNumber, dateOfBirth, country)
    }

    suspend fun updateCustomer(customerNumber: Long, firstName : String, lastName: String, address: String?, houseNumber: String?, postalCode: String, city: String, country: String?, dateOfBirth: String, phoneNumber: String) {
        serviceGenerator.updateCustomer(customerNumber, firstName, lastName, address, houseNumber, postalCode, city, country, dateOfBirth, phoneNumber)
    }

    suspend fun addCustomerImage(customerNumber: Long, image: String) {
        serviceGenerator.addCustomerImage(customerNumber, image)
    }

    suspend fun getCustomerImage(customerNumber: Long) : CustomerImageModel {
        return serviceGenerator.getCustomerImage(customerNumber)
    }
}
