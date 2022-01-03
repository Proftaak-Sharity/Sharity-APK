package com.example.sharity_apk.service


import androidx.room.Delete
import com.example.sharity_apk.model.CarModel
import retrofit2.http.*

interface CarApiService {

    @GET("cars")
    suspend fun getCars(): MutableList<CarModel>

    //    using @path so that customer_number becomes the number in the path of the GET
    @GET("cars/{licensePlate}")
    suspend fun getCar(@Path("licensePlate") licensePlate: String?) : CarModel

    @GET("cars/fuelcars")
    suspend fun getFuelCars(): MutableList<CarModel>

    @GET("cars/electriccars")
    suspend fun getElectricCars(): MutableList<CarModel>

    @GET("cars/hydrogencars")
    suspend fun getHydrogenCars(): MutableList<CarModel>

    @GET("cars/customer/{customer_number}")
    suspend fun getCars(@Path ("customer_number")customerNumber: Long): MutableList<CarModel>

    @PUT("cars/{licensePlate}")
    suspend fun updateCar(@Path ("licensePlate") licensePlate: String?,
                          @Query ( "pricePerDay") pricePerDay: Double)

    @DELETE("cars/{licensePlate}")
    suspend fun deleteCar(@Path ("licensePlate") licensePlate: String?)
}