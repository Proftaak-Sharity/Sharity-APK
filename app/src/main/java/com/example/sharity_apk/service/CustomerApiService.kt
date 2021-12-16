package com.example.sharity_apk.service

import android.text.Editable
import com.example.sharity_apk.model.Customer
import retrofit2.Call
import retrofit2.http.*

interface CustomerApiService {

    @GET ("customers")
    fun getCustomers(): Call<MutableList<Customer>>

    @POST ("customers/login")
    suspend fun getUser(@Query("email") email: String, @Query("password") password: String) : Long

//    @POST("/login?user={user}&pass={password}")
//    fun henk(username: String, password: String): Int

//    @GET ("customers")
//    fun getLoginData(email: String, password: String): Call<MutableList<Customer>> {
//        return getLoginData().
//    }

}