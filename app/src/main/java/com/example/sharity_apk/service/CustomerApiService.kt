package com.avans.avd.demoretrofit

import com.example.sharity_apk.model.Customer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:8080"
//    "http://10.0.26.2:8080/"

// For parsing the json result: add a Moshi builder
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    // A converter for strings and both primitives and their boxed types to text/plain bodies.
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Here we define how Retrofit interacts with the webservice
// we create 'suspend' fun, so we can call the function from a coroutine scope
interface CustomerApiService {
    @GET("/api/customers")
    suspend fun getCustomers(): String

//    @DELETE("api/customers/{id}")
//    suspend fun deleteCustomer(@Path("id") customerNumber: Long)
//
//    @POST(value = "api/customers/")
//    suspend fun addCustomer(@Body customer: CustomerModel): CustomerModel
}

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}