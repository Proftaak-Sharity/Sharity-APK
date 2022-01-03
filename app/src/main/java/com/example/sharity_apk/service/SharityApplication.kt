package com.example.sharity_apk.service

import android.app.Application
import com.example.sharity_apk.room.AppDatabase

class SharityApplication: Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}