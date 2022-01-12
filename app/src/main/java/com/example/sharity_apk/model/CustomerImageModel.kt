package com.example.sharity_apk.model

import com.squareup.moshi.Json

class CustomerImageModel (

    @field:Json(name = "customerNumber")
    val customerNumber: Long,

    @field:Json(name = "image")
    val image: String,

    )