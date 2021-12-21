package com.example.sharity_apk.model

import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.squareup.moshi.Json


enum class CarType(val type: String){
    ELECTRIC("Electric"),
    FUEL("Fuel"),
    HYDROGEN("Hydrogen")
}

data class CarModel(

    @field:Json(name = "licence_plate")
    val licencePlate: String?,

    @field:Json(name = "car_type")
    val carType: String?,
//    var carType: String = carTypeEnum.toString(),

    @field:Json(name = "customer_Number")
    val customerNumber: Long?,

    @field:Json(name = "make")
    val make: String?,

    @field:Json(name = "model")
    val model: String?,

    @field:Json(name = "price_per_day")
    val pricePerDay: String?,

    @field:Json(name = "price_per_km")
    val pricePerKm: String?,

    @field:Json(name = "battery_capacity")
    val batteryCapacity: String?,

    @field:Json(name = "fast_charging_time")
    val fatsChargingTime: String?,

    @field:Json(name = "km_per_kw")
    val kmPerKw: String?,

    @field:Json(name = "fuel_type")
    val fuelType: String?,

    @field:Json(name = "km_per_liter_fuel")
    val kmPerLiterFuel: String?,

    @field:Json(name = "size_fueltank")
    val sizeFueltank: String?,

    @field:Json(name = "km_per_kilo")
    val kmPerKilo: String?,
    ) {

}

