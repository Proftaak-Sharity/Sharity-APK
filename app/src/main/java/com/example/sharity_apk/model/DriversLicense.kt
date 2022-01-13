package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class DriversLicense(

    @field:Json(name = "country")
    val country: String?,

    @field:Json(name = "customerNumber")
    val customerNumber: Long?,

    @field:Json(name = "licenseNumber")
    val licenseNumber: String?,

    @field:Json(name = "validUntil")
    val validUntil: String?
)