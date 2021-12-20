package com.example.sharity_apk.model

import java.text.DecimalFormat
import java.util.*

data class ReservationModel (
        val reservationNumber: String,
        val licensePlate: String,
        val customerNumber: Long,
        val rent: Int,
        val kmPackage: Int,
        val packagePrice: Int,
        val reservationDate: String,
        val startDate: String,
        val endDate: String,
        val paymentEnum: String
)