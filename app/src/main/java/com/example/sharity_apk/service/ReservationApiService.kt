package com.example.sharity_apk.service


import com.example.sharity_apk.model.ReservationModel
import com.google.type.DateTime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface ReservationApiService {

    @GET("reservations/customer/{customer_number}")
    suspend fun getReservations(@Path("customer_number") customerNumber: Long) : MutableList<ReservationModel>

    @GET ("reservations/{reservation_number}")
    suspend fun getReservation(@Path("reservation_number") reservationNumber: Int) : ReservationModel

    @GET ("reservations/rentedLicences")
    suspend fun getRentedCars(@Query("startDate") startDate: LocalDate?,
                              @Query("endDate") endDate: LocalDate?
    ) : MutableList<ReservationModel>



}