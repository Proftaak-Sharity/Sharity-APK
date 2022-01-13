package com.example.sharity_apk.model

import com.squareup.moshi.Json

class CarImage (

        @field:Json(name = "licensePlate")
        val licensePlate: String,

        @field:Json(name = "image")
        val image: String,

    )