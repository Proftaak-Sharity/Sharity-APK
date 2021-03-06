package com.example.sharity_apk.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sharity_apk.model.Bankaccount

@Database(entities = [Bankaccount::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bankaccountDao(): BankaccountDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                    instance
            }
        }
    }
}