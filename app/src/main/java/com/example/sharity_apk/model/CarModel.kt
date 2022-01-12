package com.example.sharity_apk.model

import android.graphics.Picture
import com.squareup.moshi.Json



data class CarModel(

    @field:Json(name = "licensePlate")
    val licensePlate: String?,

    @field:Json(name = "customerNumber")
    val customerNumber: Long?,

    @field:Json(name = "make")
    val make: String?,

    @field:Json(name = "model")
    val model: String?,

    @field:Json(name = "pricePerDay")
    val pricePerDay: String?,

    @field:Json(name = "price_per_km")
    val pricePerKm: String,

    @field:Json(name = "battery_capacity")
    val batteryCapacity: String?,

    @field:Json(name = "km_per_kw")
    val kmPerKw: String?,

    @field:Json(name = "fuel_type")
    val fuelType: String?,

    @field:Json(name = "km_per_liter")
    val kmPerLiterFuel: String?,

    @field:Json(name = "size_fueltank")
    val sizeFueltank: String?,

    @field:Json(name = "km_per_kilo")
    val kmPerKilo: String?

    )