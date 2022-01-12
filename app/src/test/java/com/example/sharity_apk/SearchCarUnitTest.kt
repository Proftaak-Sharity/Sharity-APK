package com.example.sharity_apk

import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchCarUnitTest {
    @DelicateCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val sut: CarApiService = Mockito.mock(CarApiService::class.java)

    @DelicateCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @DelicateCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getCarUnitTest(): Unit = runBlocking {

        val car = makeCar()

        launch(Dispatchers.Main){

            Mockito.`when`(sut.getCar("test")).thenReturn(car)
            val carToTest: CarModel = sut.getCar("test")

            return@launch assertEquals(carToTest.customerNumber, car.customerNumber )

        }
    }

    @Test
    fun getCarsUnitTest(): Unit = runBlocking {
        val numberOfCars: Long = 2


        launch(Dispatchers.Main){
            val carList = makeCars(numberOfCars)
            Mockito.`when`(sut.getCars()).thenReturn(carList)
            val carListToCount: MutableList<CarModel> = sut.getCars()

            return@launch assertEquals(carListToCount.size, carList.size )

        }
    }

    private fun makeCar(): CarModel {

        return CarModel(
            licensePlate = "AUTO01",
            customerNumber = 12,
            make = "OPEL",
            model = "Astra",
            pricePerDay = "50",
            pricePerKm = "0.23",
            fuelType = "Petrol",
            kmPerLiterFuel = "20",
            sizeFueltank = "35",
            batteryCapacity = "",
            kmPerKilo = "",
            kmPerKw = "",
        )
    }

    private fun makeCars(number: Long): MutableList<CarModel> {

        val carList = mutableListOf<CarModel>()

        var i: Long = 1

        while (i <= number) {
            val car = CarModel(  licensePlate = "AUTO0$i",
                customerNumber = i,
                make = "OPEL",
                model = "Astra",
                pricePerDay = "50" ,
                pricePerKm = "0.23",
                fuelType = "Petrol",
                kmPerLiterFuel = "20",
                sizeFueltank = "35",
                batteryCapacity = "",
                kmPerKilo = "",
                kmPerKw = "",
            )

            carList.add(car)
            i++
        }
        return carList
    }

}
