package com.example.sharity_apk.service

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET ("/api/customers")
    fun getCustomers(): Call<MutableList<CustomerModel>>

}