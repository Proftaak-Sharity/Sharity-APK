package com.example.sharity_apk.service


import com.example.sharity_apk.model.CarModel
import retrofit2.http.*

interface CarApiService {

    @GET("cars")
    suspend fun getCars(): MutableList<CarModel>

    //    using @path so that customer_number becomes the number in the path of the GET
    @GET("car/{license_plate}")
    suspend fun getCar(@Path("license_plate") licensePlate: Long) : CarModel
//
//    @GET("customers/license/{customer_number}")
//    suspend fun getDriversLicense(@Path("customer_number") customerNumber: Long) : DriversLicenseModel
//
//    @GET("customers/bankaccounts/{customer_number}")
//    suspend fun getBankaccounts(@Path("customer_number") customerNumber: Long) : MutableList<BankaccountModel>
//
//    @GET("customers/bankaccounts/account/{id}")
//    suspend fun getBankaccount(@Path("id") id: Long) : BankaccountModel
//
//    @DELETE("customers/bankaccounts/delete/{id}")
//    suspend fun deleteBankaccount(@Path("id") id: Long)
//
//    @PUT("customers/bankaccounts/edit/{id}")
//    suspend fun editBankaccount(@Path("id") id: Long,
//                                @Query("iban") iban: String,
//                                @Query("accountHolder") accountHolder: String)

}