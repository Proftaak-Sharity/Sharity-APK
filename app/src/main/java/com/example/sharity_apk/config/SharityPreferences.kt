package com.example.sharity_apk.config

import android.content.Context
import android.content.SharedPreferences

class SharityPreferences(context: Context) {

    private val PREFERENCE_NAME = "SharedPreference"
    private val CUSTOMER_NUMBER = "CustomerNumber"
    private val CUSTOMER_LAST_NAME = "FirstName"
    private val CUSTOMER_FIRST_NAME = "LastName"
    private val LICENSE_PLATE = "LicencePlate"
    private val RESERVATION_NUMBER = "ReservationNumber"
    private val START_DATE = "StartDate"
    private val END_DATE = "EndDate"
    private val FUEL_TYPE = "FuelType"
    private val CUSTOMER_PASSWORD = "Password"
    private val CUSTOMER_EMAIL = "Email"
    private val CUSTOMER_ADDRESS = "Address"
    private val CUSTOMER_HOUSE_NUMBER = "HouseNumber"
    private val CUSTOMER_POSTAL_CODE = "PostalCode"
    private val CUSTOMER_CITY = "City"
    private val CUSTOMER_DATE_OF_BIRTH = "DateOfBirth"
    private val CUSTOMER_PHONE = "Phone"
    private val CUSTOMER_COUNTRY = "Country"
    private val LICENSE_COUNTRY = "LicenseCountry"
    private val LICENSE_NUMBER = "LicenseNumber"
    private val LICENSE_VALID_UNTIL = "ValidUntil"
    private val BANKACCOUNT_ID = "BankaccountId"
    private val CARTYPE = "CarType"
    private val IMAGE = "Image"

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

    fun getBankaccountId(): Int {
        return preference.getInt(BANKACCOUNT_ID, 0)
    }

    fun setBankaccountId(id: Int) {
        preference.edit().putInt(BANKACCOUNT_ID, id).apply()
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

    fun getHouseNumber(): String? {
        return preference.getString(CUSTOMER_HOUSE_NUMBER, "")
    }

    fun setPostalCode(postalCode: String) {
        return preference.edit().putString(CUSTOMER_POSTAL_CODE, postalCode).apply()
    }

    fun getPostalCode(): String? {
        return preference.getString(CUSTOMER_POSTAL_CODE, "")
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

    fun getLicensePlate() : String? {
        return preference.getString(LICENSE_PLATE, "RGBB54")
    }

    fun setLicensePlate(carId: String?) {
        preference.edit().putString(LICENSE_PLATE, carId).apply()
    }

    fun setCity(city: String) {
        return preference.edit().putString(CUSTOMER_CITY, city).apply()
    }

    fun getCity(): String? {
        return preference.getString(CUSTOMER_CITY, "")
    }

    fun setStartDate(startDate: String) {
        preference.edit().putString(START_DATE, startDate).apply()
    }

    fun getStartDate(): String? {
        return preference.getString(START_DATE, "")
    }

    fun setEndDate(endDate: String) {
        preference.edit().putString(END_DATE, endDate).apply()
    }

    fun getEndDate(): String? {
        return preference.getString(END_DATE, "")
    }

    fun setFuelType(fuel: String) {
        preference.edit().putString(FUEL_TYPE, fuel).apply()
    }

    fun getFuelType(): String? {
        return preference.getString(FUEL_TYPE, "")
    }

    fun setCountry(country: String) {
        preference.edit().putString(CUSTOMER_COUNTRY, country).apply()
    }

    fun getCountry(): String? {
        return preference.getString(CUSTOMER_COUNTRY, "")
    }

    fun setLicenseNumber(licenseNumber: String) {
        preference.edit().putString(LICENSE_NUMBER, licenseNumber).apply()
    }

    fun getLicenseNumber(): String? {
        return preference.getString(LICENSE_NUMBER, "")
    }

    fun setValidUntil(validUntil: String) {
        preference.edit().putString(LICENSE_VALID_UNTIL, validUntil).apply()
    }

    fun getValidUntil(): String? {
        return preference.getString(LICENSE_VALID_UNTIL, "")
    }

    fun setCountryLicense(countryLicense: String) {
        return preference.edit().putString(LICENSE_COUNTRY, countryLicense).apply()
    }

    fun getCountryLicense(): String? {
        return preference.getString(LICENSE_COUNTRY, "")
    }

    fun setAddedCarType(carType: String) {
        return preference.edit().putString(CARTYPE, carType).apply()
    }

    fun getAddedCarType(): String? {
        return preference.getString(CARTYPE, "")
    }

    fun getImage(): String? {
        return preference.getString(IMAGE, "")
    }

    fun setImage(image: String) {
        return preference.edit().putString(IMAGE, image).apply()
    }


}
