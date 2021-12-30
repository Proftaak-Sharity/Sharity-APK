package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class DriversLicenseModel(

    @field:Json(name = "country")
    val country: String?,

    @field:Json(name = "customer_number")
    val customerNumber: Long?,

    @field:Json(name = "license_number")
    val licenseNumber: String?,

    @field:Json(name = "valid_until")
    val validUntil: String?
)