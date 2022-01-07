package com.example.sharity_apk

import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchCarUnitTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    val sut = Mockito.mock(CarApiService::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun getCarUnitTest(): Unit = runBlocking {

        var car = makeCar()

        launch(Dispatchers.Main){

            Mockito.`when`(sut.getCar("test")).thenReturn(car)
            var carToTest: CarModel = sut.getCar("test")

            return@launch assertEquals(carToTest.customerNumber, car.customerNumber )

        }

    }

    @Test
    fun getCarsUnitTest(): Unit = runBlocking {
        val numberOfCars: Long = 8


        launch(Dispatchers.Main){
            var carList = MakeCars(numberOfCars)
            Mockito.`when`(sut.getCars()).thenReturn(carList)
            var carListToCount: MutableList<CarModel> = sut.getCars()

            return@launch assertEquals(carListToCount.size, carList.size )

        }

    }


    fun makeCar() : CarModel {
        var car = CarModel(  licensePlate = "AUTO01",
            carType = "FUEL",
            customerNumber = 12,
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
            fatsChargingTime = ""
        )

        return car
    }

    fun MakeCars(number: Long): MutableList<CarModel> {

        var carList = mutableListOf<CarModel>()

        var i: Long = 0

        while (i <= number) {
            var car = CarModel(  licensePlate = "AUTO0$i",
                carType = "FUEL",
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
                fatsChargingTime = ""
            )

            carList.add(car)
        }
        return carList
    }

}

