package com.example.sharity_apk.service

import android.text.Editable
import com.example.sharity_apk.model.Customer
import retrofit2.Call
import retrofit2.http.*

interface CustomerApiService {

    @GET ("customers")
    suspend fun getCustomers(): MutableList<Customer>

//    using @path so that customer_number becomes the number in the path of the GET
    @GET("customers/{customer_number}")
    suspend fun getCustomer(@Path("customer_number") customerNumber: Long) : Customer

//    using Query so that email en query are used in query url, like: customers/login?email={email}&password={password}
    @POST ("customers/login")
    suspend fun getUser(@Query("email") email: String, @Query("password") password: String) : Long

}