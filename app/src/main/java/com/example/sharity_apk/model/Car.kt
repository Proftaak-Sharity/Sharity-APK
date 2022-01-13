package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class Car(

    @field:Json(name = "licensePlate")
    val licensePlate: String?,

    @field:Json(name = "customerNumber")
    val customerNumber: Long?,

    @field:Json(name = "make")
    val make: String?,

    @field:Json(name = "model")
    val model: String?,

    @field:Json(name = "pricePerDay")
    val pricePerDay: Double?,

    @field:Json(name = "price_per_km")
    val pricePerKm: Double,

    @field:Json(name = "battery_capacity")
    val batteryCapacity: Int?,

    @field:Json(name = "km_per_kw")
    val kmPerKw: Int?,

    @field:Json(name = "fuel_type")
    val fuelType: String?,

    @field:Json(name = "km_per_liter")
    val kmPerLiterFuel: Int?,

    @field:Json(name = "size_fueltank")
    val sizeFueltank: Int?,

    @field:Json(name = "km_per_kilo")
    val kmPerKilo: Int?

    )