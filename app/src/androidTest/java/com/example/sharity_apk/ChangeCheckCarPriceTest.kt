package com.example.sharity_apk

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeCheckCarPriceTest : TestFunctions() {

    @Test
    fun changeAndCheckCarPriceTest() {

        testLogin()

        onView(withId(R.id.button_my_cars))
            .perform(click())
        onView(withId(R.id.my_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.button_edit))
            .perform(click())
        onView(withHint("Price per day"))
            .perform(clearText(), closeSoftKeyboard())
            .perform(click())
            .perform(typeText("55.95"), closeSoftKeyboard())
        onView(withId(R.id.btn_save))
            .perform(click())
        onView(withText("OK"))
            .perform(click())
        onView(withText("€ 55.95")).check(matches(isDisplayed()))
    }
}