package com.example.sharity_apk

import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ReservationApiService
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

class ReservationUnitTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    val sut = Mockito.mock(ReservationApiService::class.java)

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
    fun getReservationUnitTest(): Unit = runBlocking {

        var reservation = makeReservation()

        launch(Dispatchers.Main){

            Mockito.`when`(sut.getReservation(1)).thenReturn(reservation)
            var reservationToTest: ReservationModel = sut.getReservation(1)

            return@launch assertEquals(reservationToTest.customerNumber, reservation.customerNumber )
        }
    }

    @Test
    fun getReservationsUnitTest(): Unit = runBlocking {
        val numberOfReservations: Long = 3


        launch(Dispatchers.Main){
            var reservationList = makeReservations(numberOfReservations)
            Mockito.`when`(sut.getReservations(3L)).thenReturn(reservationList)
            var reservationListToCount: MutableList<ReservationModel> = sut.getReservations(3L)

            return@launch assertEquals(reservationListToCount.size, reservationList.size )

        }

    }


    fun makeReservation() : ReservationModel {
        var reservation = ReservationModel(
            1,
            3L,
            "2022-10-29",
            "2022-11-08",
            "2022-01-11",
            "LH099X",
            600,
            100.00,
            700.00,
            "PAID"
        )

        return reservation
    }

    fun makeReservations(number: Long): MutableList<ReservationModel> {

        var reservationList = mutableListOf<ReservationModel>()

        var i: Long = 1

        while (i <= number) {
            var reservation = ReservationModel(
                1,
                3L,
                "2022-10-29",
                "2022-11-08",
                "2022-01-11",
                "LH099X",
                600,
                100.00,
                700.00,
                "PAID"
            )

            reservationList.add(reservation)
            i++
        }
        return reservationList
    }

}

