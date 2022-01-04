package com.example.sharity_apk.service

import com.example.sharity_apk.model.ReservationModel
import retrofit2.http.*
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

    @POST("reservations/addReservation")
    suspend fun addReservation(@Query("customerNumber") customerNumber: Long?,
                               @Query("licensePlate") licensePlate: String?,
                               @Query("kmPackage") kmPackage: Int?,
                               @Query("startDate") startDate: String?,
                               @Query("endDate") endDate: String?,
                               @Query("rent") rent: Double?,
                               @Query("packagePrice") packagePrice: Double?,
                               @Query("paymentEnum") paymentEnum: String?
    ) : Int
}