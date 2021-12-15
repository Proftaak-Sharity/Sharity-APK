package com.example.sharity_apk.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class Customer(

    @Json(name = "customerNumber")
    val customerNumber: Long? = null,

    @Json(name = "address")
    val address: String,

    @Json(name = "houseNumber")
    val houseNumber: String,

    @Json(name = "postalCode")
    val postalCode: String,

    @Json(name = "city")
    val city: String,

    @Json(name = "phoneNumber")
    val phoneNumber: String,

    @Json(name = "dateOfBirth")
    val dateOfBirth: LocalDate,

    @Json(name = "customerNumber")
    val country: String) {

}

annotation class Entity
