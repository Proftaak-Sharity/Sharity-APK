package com.example.sharity_apk.model

data class Reservation(
    val customerNumber: Int,
    val endDate: String,
    val kmPackage: Int,
    val licensePlate: String,
    val packagePrice: Double,
    val paymentEnum: String,
    val rent: Double,
    val reservationDate: String,
    val reservationNumber: Int,
    val startDate: String
)