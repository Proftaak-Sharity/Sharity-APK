package com.example.sharity_apk.room

import android.app.Application

class SharityApplication: Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}