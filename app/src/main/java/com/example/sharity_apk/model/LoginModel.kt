package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class LoginModel (

    @field:Json(name = "customer_number")
    val customerNumber: Long?,

    @field:Json(name = "last_name")
    val lastName: String?,

    @field:Json(name = "first_name")
    val firstName: String?,

    @field:Json(name = "email")
    val email: String?,

    @field:Json(name = "password")
    val password: String?
)