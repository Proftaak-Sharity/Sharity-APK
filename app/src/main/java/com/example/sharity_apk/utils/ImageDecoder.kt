package com.example.sharity_apk.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

class ImageDecoder() {

    fun decodeImageString(encodedString: String): Bitmap {
        val imageBytes = Base64.getDecoder().decode(encodedString)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}