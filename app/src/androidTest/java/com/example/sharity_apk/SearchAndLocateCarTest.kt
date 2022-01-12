package com.example.sharity_apk

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import androidx.recyclerview.widget.RecyclerView

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchAndLocateCarTest: TestFunctions() {

    @Before
    fun login(){
        // before we start any tests we need to login
        testLogin()
    }

    @Test
    fun searchAndLocateCarTest(){

        //go to searchCars
        onView(withId(R.id.button_make_reservation)).perform(click())

        //select hydrogen and search al hydrogen cars
        onView(withId(R.id.option_hydrogen)).perform(click())
        onView(withId(R.id.search_button)).perform(click())

        // click on the hydrogen car and see if it can be located with maps
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.button_locate)).perform(click())
    }
}
