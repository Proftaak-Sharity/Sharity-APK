package com.example.sharity_apk.service


import com.example.sharity_apk.model.ReservationModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservationApiService {

    @GET("reservations/customer/{customer_number}")
    suspend fun getReservations(@Path("customer_number") customerNumber: Long) : MutableList<ReservationModel>
}