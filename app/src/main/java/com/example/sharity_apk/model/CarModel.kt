package com.example.sharity_apk.model

import androidx.annotation.DrawableRes

data class CarModel(
    @DrawableRes val imageResourceId: Int,
    val make: String
)