package com.example.sharity_apk

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId

open class BaseTests {

    fun createCustomerFlow() {
        // Launch the main activity
        launchActivity<MainActivity>()
        // Start creating account (email / password)
        onView(withId(R.id.sign_in)).perform(click())
        // Select entree item
//        onView(withId(R.id.button_next)).perform(click())
        // Move to next fragment

    }
}