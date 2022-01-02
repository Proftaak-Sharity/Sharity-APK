package com.example.sharity_apk.service

import com.example.sharity_apk.model.CustomerModel
import com.example.sharity_apk.model.DriversLicenseModel
import com.example.sharity_apk.model.LoginModel
import com.example.sharity_apk.room.model.BankaccountModel
import retrofit2.http.*

interface CustomerApiService {

//    using @path so that customer_number becomes the number in the path of the GET
    @GET("customers/{customer_number}")
    suspend fun getCustomer(@Path("customer_number") customerNumber: Long) : CustomerModel

    @POST("customers")
    suspend fun addCustomer(@Query("firstName") firstName : String,
                            @Query("lastName") lastName: String,
                            @Query("email") email: String?,
                            @Query("password") password: String?,
                            @Query("address") address: String?,
                            @Query("houseNumber") houseNumber: String?,
                            @Query("postalCode") postalCode: String,
                            @Query("city") city: String,
                            @Query("phoneNumber") phoneNumber: String,
                            @Query("dateOfBirth") dateOfBirth: String,
                            @Query("country") country: String)

//    using Query so that email en query are used in query url, like: customers/login?email={email}&password={password}
    @GET ("customers/login")
    suspend fun getUser(@Query("email") email: String,
                        @Query("password") password: String) : LoginModel

    @GET("customers/license/{customerNumber}")
    suspend fun getDriversLicense(@Path ("customerNumber") customerNumber: Long) : DriversLicenseModel

    @GET("customers/emailcheck")
    suspend fun checkEmail(@Query("email") email: String) :Boolean

    @GET("customers/driverslicense/check")
    suspend fun checkLicense(@Query("licenseNumber") licenseNumber: String) :Boolean

    @POST ("customers/driverslicense")
    suspend fun addDriversLicense(@Query("customerNumber") customerNumber: Long,
                                  @Query("licenseNumber") licenseNumber: String,
                                  @Query("country") country: String,
                                  @Query("validUntil") validUntil: String)

    @PUT("customers/update")
    suspend fun updateCustomer(@Query("customerNumber") customerNumber: Long,
                               @Query("firstName") firstName : String,
                               @Query("lastName") lastName: String,
                               @Query("address") address: String?,
                               @Query("houseNumber") houseNumber: String?,
                               @Query("postalCode") postalCode: String,
                               @Query("city") city: String,
                               @Query("country") country: String?,
                               @Query("dateOfBirth") dateOfBirth: String,
                               @Query("phoneNumber") phoneNumber: String)
}
