package com.example.sharity_apk.service

import com.example.sharity_apk.model.Customer
import retrofit2.Call
import retrofit2.http.GET

interface CustomerApiService {

    @GET ("customers")
    fun getCustomers(): Call<MutableList<Customer>>

}