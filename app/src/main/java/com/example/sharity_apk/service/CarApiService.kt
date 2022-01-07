package com.example.sharity_apk.service


import com.example.sharity_apk.model.CarImageModel
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
    suspend fun addCarPhoto(@Query ("licensePlate") licensePlate: String,
                            @Query ("picture") picture: String
    )

    @GET("cars/image/{licensePlate}")
    suspend fun getCarPhoto(@Path ("licensePlate") licensePlate: String) : CarImageModel
}