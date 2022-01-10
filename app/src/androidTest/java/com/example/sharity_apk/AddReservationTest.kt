package com.example.sharity_apk

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddReservationTest: TestFunctions() {

    @Before
    fun login(){
        testLogin()
    }

    @Test
    fun addReservationTest(){

        //go to searchCars
        onView(withId(R.id.button_make_reservation)).perform(click())

        //select petrol and a date and go to recyclerview
        onView(withId(R.id.option_petrol)).perform(click())
        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2021, 6, 30), PickerActions.setDate(2021, 7, 30))




    }
}
