package com.example.sharity_apk.service

import com.example.sharity_apk.model.CustomerModel
import com.example.sharity_apk.model.DriversLicenseModel
import com.example.sharity_apk.model.LoginModel
import retrofit2.http.*

interface CustomerApiService {

    @GET ("customers")
    suspend fun getCustomers(): MutableList<CustomerModel>

//    using @path so that customer_number becomes the number in the path of the GET
    @GET("customers/{customer_number}")
    suspend fun getCustomer(@Path("customer_number") customerNumber: Long) : CustomerModel

//    using Query so that email en query are used in query url, like: customers/login?email={email}&password={password}
    @POST ("customers/login")
    suspend fun getUser(@Query("email") email: String, @Query("password") password: String) : LoginModel

    @POST("customers/license/{customer_number}")
    suspend fun getDriversLicense(@Path ("customer_number") customerNumber: Long) : DriversLicenseModel

}