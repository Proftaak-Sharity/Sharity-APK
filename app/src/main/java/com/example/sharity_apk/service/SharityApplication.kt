package com.example.sharity_apk.service

import android.app.Application

class SharityApplication: Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}