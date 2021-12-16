package com.example.sharity_apk.service

import com.example.sharity_apk.model.Customer
import com.example.sharity_apk.model.ReservationModel
import retrofit2.Call
import retrofit2.http.GET

interface ReservationApiService {

    @GET ("reservations")
    fun getReservations(): Call<MutableList<ReservationModel>>

}