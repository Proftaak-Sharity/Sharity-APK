package com.example.sharity_apk.service

import com.example.sharity_apk.model.Reservation
import retrofit2.http.*
import java.time.LocalDate

interface ReservationApiService {

    @GET("reservations/customer/{customer_number}")
    suspend fun getReservations(@Path("customer_number") customerNumber: Long) : MutableList<Reservation>

    @GET ("reservations/{reservation_number}")
    suspend fun getReservation(@Path("reservation_number") reservationNumber: Int) : Reservation

    @GET ("reservations/rentedLicensePlates")
    suspend fun getRentedCars(@Query("startDate") startDate: LocalDate?,
                              @Query("endDate") endDate: LocalDate?) : MutableList<Reservation>

    @POST("reservations/addReservation")
    suspend fun addReservation(@Query("customerNumber") customerNumber: Long?,
                               @Query("licensePlate") licensePlate: String?,
                               @Query("kmPackage") kmPackage: Int?,
                               @Query("startDate") startDate: String?,
                               @Query("endDate") endDate: String?,
                               @Query("rent") rent: Double?,
                               @Query("packagePrice") packagePrice: Double?,
                               @Query("payment") payment: String?) : Int

    @PUT("reservations/payment/{reservation_number}")
    suspend fun updatePayment(@Path ("reservation_number") reservationNumber: Int,
                              @Query ("payment") payment: String?) : String
}