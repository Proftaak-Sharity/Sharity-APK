package com.example.sharity_apk.config

import android.content.Context
import android.content.SharedPreferences


class SharityPreferences(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val CUSTOMER_NUMBER  = "CustomerNumber"
    val CUSTOMER_LAST_NAME  = "FirstName"
    val CUSTOMER_FIRST_NAME  = "LastName"
    val CUSTOMER_IBAN = "Iban"
    val LICENSE_PLATE = "LicencePlate"
    val CITY = "City"
    val RESERVATION_NUMBER = "ReservationNumber"
    val START_DATE = "StartDate"
    val END_DATE = "EndDate"
    val FUEL_TYPE = "FuelType"


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

    fun getLicensePlate() : String? {
        return preference.getString(LICENSE_PLATE, "RGBB54")
    }

    fun setLicensePlate(carId: String?) {
        preference.edit().putString(LICENSE_PLATE, carId).apply()

    }

    fun setCity(city: String) {
        return preference.edit().putString(CITY, null).apply()
    }

    fun getCity(): String? {
        return preference.getString(CITY, "")
    }

    fun setStartDate(startDate: Long) {
        preference.edit().putLong(START_DATE, startDate).apply()
    }

    fun getStartDate(): Long? {
        return preference.getLong(START_DATE, 0)
    }

    fun setEndDate(endDate: Long) {
        preference.edit().putLong(END_DATE, endDate).apply()
    }

    fun getEndDate(): Long? {
        return preference.getLong(END_DATE, 0)
    }

    fun setFuelType(fuel: String) {
        preference.edit().putString(FUEL_TYPE, fuel).apply()
    }

    fun getFuelType(): String? {
        return preference.getString(FUEL_TYPE, "")
    }
}
