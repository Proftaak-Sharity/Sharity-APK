package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.CarImageModel
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CarViewModel(): ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)

    suspend fun getCars(): MutableList<CarModel> {
        return serviceGenerator.getCars()
    }

    suspend fun getFuelCars(): MutableList<CarModel> {
        return serviceGenerator.getFuelCars()
    }

    suspend fun getElectricCars(): MutableList<CarModel> {
        return serviceGenerator.getElectricCars()
    }

    suspend fun getHydrogenCars(): MutableList<CarModel> {
        return serviceGenerator.getHydrogenCars()
    }

    suspend fun getCarsFromCustomer(customerNumber: Long): MutableList<CarModel> {
        return serviceGenerator.getCarsFromCustomer(customerNumber)
    }

    suspend fun deleteCar(licensePlate: String?) {
        serviceGenerator.deleteCar(licensePlate)
    }

    suspend fun addElectricCar(licensePlate: String, customerNumber: Long, make: String, model: String, pricePerDay: Double, batteryCapacity: Int, kmPerKw: Int) {
        serviceGenerator.addElectricCar(licensePlate, customerNumber, make, model, pricePerDay, batteryCapacity, kmPerKw)
    }

    suspend fun addHydrogenCar(licensePlate: String, customerNumber: Long, make: String, model: String, pricePerDay: Double, sizeFueltank: Int, kmPerKilo: Int) {
        serviceGenerator.addHydrogenCar(licensePlate, customerNumber, make, model, pricePerDay, sizeFueltank, kmPerKilo)
    }

    suspend fun addFuelCar(licensePlate: String, customerNumber: Long, make: String, model: String, pricePerDay: Double, sizeFueltank: Int, kmPerLiter: Int, fuelType: String) {
        serviceGenerator.addFuelCar(licensePlate, customerNumber, make, model, pricePerDay, sizeFueltank, kmPerLiter, fuelType)
    }

    suspend fun updateCar(licensePlate: String?, pricePerDay: Double) {
        serviceGenerator.updateCar(licensePlate, pricePerDay)
    }

    suspend fun getCar(licensePlate: String?) : CarModel {
        return serviceGenerator.getCar(licensePlate)
    }

    suspend fun addCarImage(licensePlate: String, image: String) {
         serviceGenerator.addCarImage(licensePlate, image) }

    suspend fun getCarImage(licensePlate: String): CarImageModel {
        return serviceGenerator.getCarImage(licensePlate)
    }

    suspend fun checkAvailability(start: String?, end: String?, carList: MutableList<CarModel>): MutableList<CarModel> {
        val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val licensePlates = mutableListOf<String?>()
        val carsToBeRemoved = mutableListOf<CarModel?>()

        // if we have no start or end date we show all cars
        if ((start.isNullOrEmpty()) and (end.isNullOrEmpty())){
            return carList
        } else {
            // check which cars are rented out
            val startDate = LocalDate.parse(start, formatter)
            val endDate = LocalDate.parse(end, formatter)
            try {
                // get all reservations and append licenses to list, check list and remove car if needed
                val reserved =
                    serviceGenerator.getRentedCars(startDate = startDate, endDate = endDate)
                for (car in reserved) {
                    licensePlates += car.licensePlate
                }
                for (car in carList){
                    if (car.licensePlate in licensePlates) {
                        carsToBeRemoved += car
                    }
                }
                carList.removeAll(carsToBeRemoved.toSet())
            }  catch (e: Exception) {
                return carList
            }
            carList.removeAll(carsToBeRemoved.toSet())
        }
        return carList
    }
}

