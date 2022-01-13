package com.example.sharity_apk

import com.example.sharity_apk.model.Customer
import com.example.sharity_apk.service.CustomerApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CustomerUnitTests {

    @DelicateCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val sut: CustomerApiService = Mockito.mock(CustomerApiService::class.java)

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
    fun getCustomerUnitTest(): Unit = runBlocking {

        val customer = createCustomer()

        launch(Dispatchers.Main){

            Mockito.`when`(sut.getCustomer(7)).thenReturn(customer)
            val customerToTest: Customer = sut.getCustomer(7)

            return@launch Assert.assertEquals(customerToTest, customer)

        }

    }

    private fun createCustomer(): Customer {

        return Customer(
            customerNumber = 7,
            lastName = "Ronaldo",
            firstName = "Christiano",
            dateOfBirth = "12-07-1986",
            address = "Portugalstraat",
            houseNumber = "31",
            postalCode = "2658DH",
            city = "Den Haag",
            country = "NETHERLANDS",
            phoneNumber = "0658911456",
            email = "c.ronaldo@voetbal.nl",
            password = "welkom01",
            balance = 0.0
        )
    }


}