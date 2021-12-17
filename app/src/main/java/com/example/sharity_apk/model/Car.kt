package com.example.sharity_apk.model

data class Car(
    val customerNumber: Int,
    val fuelType: String,
    val insurance: Insurance,
    val kmPerLiterFuel: Int,
    val licensePlate: String,
    val make: String,
    val model: String,
    val pricePerDay: Double,
    val pricePerKm: Double,
    val reservations: List<Reservation>,
    val sizeFueltank: Int
)