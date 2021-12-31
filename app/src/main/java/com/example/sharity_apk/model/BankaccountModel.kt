package com.example.sharity_apk.room.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "bankaccount")
data class BankaccountModel(

    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,

    @NonNull @ColumnInfo(name = "customer_number")
    val customerNumber: Long,

    @NonNull @ColumnInfo(name = "iban")
    val iban: String,

    @NonNull @ColumnInfo(name = "account_holder")
    val accountHolder: String
)