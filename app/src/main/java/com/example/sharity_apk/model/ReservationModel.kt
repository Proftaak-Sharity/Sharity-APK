package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class ReservationModel(

    @field:Json(name = "reservation_number")
    val reservationNumber: Int,

    @field:Json(name = "customer_number")
    val customerNumber: Long?,

    @field:Json(name = "start_date")
    val startDate: String?,

    @field:Json(name = "end_data")
    val endDate: String?,

    @field:Json(name = "reservation_date")
    val reservationDate: String?,

    @field:Json(name = "license_plate")
    val licensePlate: String?,

    @field:Json(name = "km_package")
    val kmPackage: Int?,

    @field:Json(name = "package_price")
    val packagePrice: Double?,

    @field:Json(name = "prent")
    val rent: Double?,

    @field:Json(name = "payment_enum")
    val paymentEnum: String?,

)