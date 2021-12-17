package com.example.sharity_apk.config

import android.content.Context
import android.content.SharedPreferences


class SharityPreferences(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_CUSTOMER_NUMBER  = "CustomerNumber"

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCustomerNumber() : Long {
        return preference.getLong(PREFERENCE_CUSTOMER_NUMBER, 0)
    }

    fun setCustomerNumber(customerNumber: Long) {
        preference.edit().putLong(PREFERENCE_CUSTOMER_NUMBER, customerNumber).apply()
    }

    fun clearPreferences() {
        preference.edit().clear().apply()
    }
}