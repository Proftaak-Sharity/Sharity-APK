package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ServiceGenerator

class CarViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)


    suspend fun addElectricCar( licensePlate: String,
                                customerNumber: Long,
                                make: String,
                                model: String,
                                pricePerDay: Double,
                                batteryCapacity: Int,
                                kmPerKw: Int) {

        serviceGenerator.addElectricCar(licensePlate, customerNumber, make, model, pricePerDay, batteryCapacity, kmPerKw)
    }

    suspend fun addHydrogenCar( licensePlate: String,
                                customerNumber: Long,
                                make: String,
                                model: String,
                                pricePerDay: Double,
                                sizeFueltank: Int,
                                kmPerKilo: Int) {

        serviceGenerator.addHydrogenCar(licensePlate, customerNumber, make, model, pricePerDay, sizeFueltank, kmPerKilo)

    }

    suspend fun addFuelCar(licensePlate: String,
                           customerNumber: Long,
                           make: String,
                           model: String,
                           pricePerDay: Double,
                           sizeFueltank: Int,
                           kmPerLiter: Int,
                           fuelType: String) {

        serviceGenerator.addFuelCar(licensePlate, customerNumber, make, model, pricePerDay, sizeFueltank, kmPerLiter, fuelType)

    }
}
