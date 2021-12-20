package com.example.sharity_apk.config

import android.content.Context
import android.content.SharedPreferences


class SharityPreferences(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val CUSTOMER_NUMBER  = "CustomerNumber"
    val CUSTOMER_LAST_NAME  = "FirstName"
    val CUSTOMER_FIRST_NAME  = "LastName"
    val CUSTOMER_IBAN = "Iban"

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCustomerNumber() : Long {
        return preference.getLong(CUSTOMER_NUMBER, 0)
    }

    fun setCustomerNumber(customerNumber: Long) {
        preference.edit().putLong(CUSTOMER_NUMBER, customerNumber).apply()
    }

    fun getLastName() : String? {
        return preference.getString(CUSTOMER_LAST_NAME, "")
    }

    fun setLastName(lastName: String) {
        preference.edit().putString(CUSTOMER_LAST_NAME, lastName).apply()
    }

    fun getFirstName() : String? {
        return preference.getString(CUSTOMER_FIRST_NAME, "")
    }

    fun setFirstName (firstName: String) {
        preference.edit().putString(CUSTOMER_FIRST_NAME, firstName).apply()
    }

    fun getBankaccount() : Long {
        return preference.getLong(CUSTOMER_IBAN, 0)
    }

    fun setBankaccount (id: Long) {
        preference.edit().putLong(CUSTOMER_IBAN, id).apply()
    }

    fun clearPreferences() {
        preference.edit().clear().apply()
    }
}