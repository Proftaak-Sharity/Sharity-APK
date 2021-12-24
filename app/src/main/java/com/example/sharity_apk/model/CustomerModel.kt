package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class CustomerModel(

    @field:Json(name = "customer_number")
    val customerNumber: Long,

    @field:Json(name = "last_name")
    val lastName: String?,

    @field:Json(name = "first_name")
    val firstName: String?,

    @field:Json(name = "date_of_birth")
    val dateOfBirth: String,

    @field:Json(name = "address")
    val address: String?,

    @field:Json(name = "house_number")
    val houseNumber: String?,

    @field:Json(name = "postal_code")
    val postalCode: String?,

    @field:Json(name = "city")
    val city: String?,

    @field:Json(name = "country")
    val country: String?,

    @field:Json(name = "phone_number")
    val phoneNumber: String?,

    @field:Json(name = "email")
    val email: String?,

    @field:Json(name = "password")
    val password: String?,

    @field:Json(name = "drivers_license")
    val driversLicense: String?
    )
