package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class BankaccountModel(

    @field:Json(name = "customer_number")
    val customerNumber: Long,

    @field:Json(name = "account_holder")
    val accountHolder: String,

    @field:Json(name = "iban")
    val iban: String
)