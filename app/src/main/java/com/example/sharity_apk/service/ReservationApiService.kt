package com.example.sharity_apk.service


import com.example.sharity_apk.model.BankaccountModel
import com.example.sharity_apk.model.ReservationModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservationApiService {

    @GET("reservations/customer/{customer_number}")
    suspend fun getReservations(@Path("customer_number") customerNumber: Long) : MutableList<ReservationModel>

    @GET ("reservations/{reservation_number}")
    suspend fun getReservation(@Query("reservation_number") reservationNumber: Int) : ReservationModel

}