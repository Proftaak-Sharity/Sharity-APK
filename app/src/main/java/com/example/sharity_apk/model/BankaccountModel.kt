package com.example.sharity_apk.model

import com.squareup.moshi.Json

data class BankaccountModel(

    @field:Json(name= "id")
    val id: Long,

    @field:Json(name = "customer_number")
    val customerNumber: Long,

    @field:Json(name = "account_holder")
    var accountHolder: String?,

    @field:Json(name = "iban")
    var iban: String?
)