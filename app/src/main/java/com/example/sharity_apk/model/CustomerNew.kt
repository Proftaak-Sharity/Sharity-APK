package com.example.sharity_apk.model

data class CustomerNew(
    val address: String,
    val balance: Double,
    val bankaccounts: List<Bankaccount>,
    val cars: List<Car>,
    val city: String,
    val country: String,
    val customerNumber: Int,
    val dateOfBirth: String,
    val driversLicense: DriversLicense,
    val email: String,
    val firstName: String,
    val houseNumber: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val postalCode: String,
    val reservations: List<ReservationX>
)