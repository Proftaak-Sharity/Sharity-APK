package com.example.sharity_apk.service


import com.example.sharity_apk.model.CarImage
import com.example.sharity_apk.model.Car
import retrofit2.http.*

interface CarApiService {

    @GET("cars")
    suspend fun getCars(): MutableList<Car>

    //    using @path so that customer_number becomes the number in the path of the GET
    @GET("cars/{licensePlate}")
    suspend fun getCar(@Path("licensePlate") licensePlate: String?) : Car

    @GET("cars/fuelcars")
    suspend fun getFuelCars(): MutableList<Car>

    @GET("cars/electriccars")
    suspend fun getElectricCars(): MutableList<Car>

    @GET("cars/hydrogencars")
    suspend fun getHydrogenCars(): MutableList<Car>

    @GET("cars/customer/{customerNumber}")
    suspend fun getCarsFromCustomer(@Path ("customerNumber")customerNumber: Long): MutableList<Car>

    @PUT("cars/{licensePlate}")
    suspend fun updateCar(@Path ("licensePlate") licensePlate: String?,
                          @Query ( "pricePerDay") pricePerDay: Double)

    @DELETE("cars/{licensePlate}")
    suspend fun deleteCar(@Path ("licensePlate") licensePlate: String?)

    @POST("cars")
    suspend fun addElectricCar(@Query ("licensePlate") licensePlate: String,
                               @Query ("customerNumber") customerNumber: Long,
                               @Query ("make") make: String,
                               @Query ("model") model: String,
                               @Query ("pricePerDay") pricePerDay: Double,
                               @Query ("batteryCapacity") batteryCapacity: Int,
                               @Query ("kmPerKw") kmPerKw: Int)

    @POST("cars")
    suspend fun addHydrogenCar(@Query ("licensePlate") licensePlate: String,
                               @Query ("customerNumber") customerNumber: Long,
                               @Query ("make") make: String,
                               @Query ("model") model: String,
                               @Query ("pricePerDay") pricePerDay: Double,
                               @Query ("sizeFueltank") sizeFueltank: Int,
                               @Query ("kmPerKilo") kmPerKilo: Int)

    @POST("cars")
    suspend fun addFuelCar(@Query ("licensePlate") licensePlate: String,
                           @Query ("customerNumber") customerNumber: Long,
                           @Query ("make") make: String,
                           @Query ("model") model: String,
                           @Query ("pricePerDay") pricePerDay: Double,
                           @Query ("sizeFueltank") sizeFueltank: Int,
                           @Query ("kmPerLiter") kmPerLiter: Int,
                           @Query ("fuelType") fuelType: String)

    @POST("cars/image")
    suspend fun addCarImage(@Query ("licensePlate") licensePlate: String,
                            @Query ("image") image: String)

    @GET("cars/image/{licensePlate}")
    suspend fun getCarImage(@Path ("licensePlate") licensePlate: String) : CarImage
}