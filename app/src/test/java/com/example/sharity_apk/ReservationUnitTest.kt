package com.example.sharity_apk

import com.example.sharity_apk.model.Reservation
import com.example.sharity_apk.service.ReservationApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ReservationUnitTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val sut: ReservationApiService = Mockito.mock(ReservationApiService::class.java)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun getReservationUnitTest(): Unit = runBlocking {

        val reservation = makeReservation()

        launch(Dispatchers.Main){

            Mockito.`when`(sut.getReservation(1)).thenReturn(reservation)
            val reservationToTest: Reservation = sut.getReservation(1)

            return@launch assertEquals(reservationToTest.customerNumber, reservation.customerNumber )
        }
    }

    @Test
    fun getReservationsUnitTest(): Unit = runBlocking {
        val numberOfReservations: Long = 3


        launch(Dispatchers.Main){
            val reservationList = makeReservations(numberOfReservations)
            Mockito.`when`(sut.getReservations(3L)).thenReturn(reservationList)
            val reservationListToCount: MutableList<Reservation> = sut.getReservations(3L)

            return@launch assertEquals(reservationListToCount.size, reservationList.size )

        }

    }


    private fun makeReservation() : Reservation {
        val reservation = Reservation(
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

    private fun makeReservations(number: Long): MutableList<Reservation> {

        val reservationList = mutableListOf<Reservation>()

        var i: Long = 1

        while (i <= number) {
            val reservation = Reservation(
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

