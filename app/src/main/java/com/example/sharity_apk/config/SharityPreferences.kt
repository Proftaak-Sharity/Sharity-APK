package com.example.sharity_apk.config

import android.content.Context
import android.content.SharedPreferences


class SharityPreferences(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val CUSTOMER_NUMBER = "CustomerNumber"
    val CUSTOMER_LAST_NAME = "FirstName"
    val CUSTOMER_FIRST_NAME = "LastName"
    val CUSTOMER_IBAN = "Iban"
    val RESERVATION_NUMBER = "ReservationNumber"
    val CUSTOMER_PASSWORD = "Password"
    val CUSTOMER_EMAIL = "Email"
    val CUSTOMER_ADDRESS = "Address"
    val CUSTOMER_HOUSE_NUMBER = "HouseNumber"
    val CUSTOMER_POSTAL_CODE = "PostalCode"
    val CUSTOMER_CITY = "City"
    val CUSTOMER_DATE_OF_BIRTH = "DateOfBirth"
    val CUSTOMER_PHONE = "Phone"

    private val preference: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCustomerNumber(): Long {
        return preference.getLong(CUSTOMER_NUMBER, 0)
    }

    fun setCustomerNumber(customerNumber: Long) {
        preference.edit().putLong(CUSTOMER_NUMBER, customerNumber).apply()
    }

    fun getLastName(): String? {
        return preference.getString(CUSTOMER_LAST_NAME, "")
    }

    fun setLastName(lastName: String) {
        preference.edit().putString(CUSTOMER_LAST_NAME, lastName).apply()
    }

    fun getFirstName(): String? {
        return preference.getString(CUSTOMER_FIRST_NAME, "")
    }

    fun setFirstName(firstName: String) {
        preference.edit().putString(CUSTOMER_FIRST_NAME, firstName).apply()
    }

    fun getBankaccount(): Long {
        return preference.getLong(CUSTOMER_IBAN, 0)
    }

    fun setBankaccount(id: Long) {
        preference.edit().putLong(CUSTOMER_IBAN, id).apply()
    }

    fun getReservationNumber(): Int {
        return preference.getInt(RESERVATION_NUMBER, 0)
    }

    fun setReservationNumber(reservationNumber: Int) {
        preference.edit().putInt(RESERVATION_NUMBER, reservationNumber).apply()
    }

    fun getPassword(): String? {
        return preference.getString(CUSTOMER_PASSWORD, "")
    }

    fun setPassword(password: String) {
        preference.edit().putString(CUSTOMER_PASSWORD, password).apply()
    }

    fun setEmail(email: String) {
        return preference.edit().putString(CUSTOMER_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return preference.getString(CUSTOMER_EMAIL, "")
    }

    fun setAddress(address: String) {
        preference.edit().putString(CUSTOMER_ADDRESS, address).apply()
    }

    fun getAddress(): String? {
        return preference.getString(CUSTOMER_ADDRESS, "")
    }

    fun setHouseNumber(houseNumber: String) {
        preference.edit().putString(CUSTOMER_HOUSE_NUMBER, houseNumber).apply()
    }

    fun getHouseNumber() : String? {
        return preference.getString(CUSTOMER_HOUSE_NUMBER, "")
    }

    fun setPostalCode(postalCode: String) {
        return preference.edit().putString(CUSTOMER_POSTAL_CODE, postalCode).apply()
    }

    fun getPostalCode(): String? {
        return preference.getString(CUSTOMER_POSTAL_CODE, "")
    }

    fun setCity(city: String) {
        preference.edit().putString(CUSTOMER_CITY, city).apply()
    }

    fun getCity(): String? {
        return preference.getString(CUSTOMER_CITY, "")
    }

    fun setDateOfBirth(dateOfBirth: String) {
        preference.edit().putString(CUSTOMER_DATE_OF_BIRTH, dateOfBirth).apply()
    }

    fun getDateOfBirth(): String? {
        return preference.getString(CUSTOMER_DATE_OF_BIRTH, "")
    }

    fun setPhone(phone: String) {
        return preference.edit().putString(CUSTOMER_PHONE, phone).apply()
    }

    fun getPhone(): String? {
        return preference.getString(CUSTOMER_PHONE, "")
    }

    fun clearPreferences() {
        preference.edit().clear().apply()
    }
}
