package com.example.sharity_apk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
class CreateAccountTest : BaseTests() {

    @Test
    fun createCustomerTest() {

        launchActivity<MainActivity>()

//        Click sign in button
        onView(withId(R.id.sign_in)).perform(click())

//        Fill in the edittext
        onView(withId(R.id.email_edittext))
            .perform(typeText("mijntest@espresso.com"), closeSoftKeyboard())
        onView(withId(R.id.password_edittext))
            .perform(typeText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.password_confirm_edittext))
            .perform(typeText("12345678"), closeSoftKeyboard())

//        click next button and go to Create Customer Fragment
        onView(withId(R.id.button_next))
            .perform(click())
//
////        Fill in the edittext
        onView(withId(R.id.ev_first_name))
            .perform(typeText("Sharity"), closeSoftKeyboard())
        onView(withId(R.id.ev_last_name))
            .perform(typeText("Tester"), closeSoftKeyboard())
        onView(withId(R.id.ev_address))
            .perform(typeText("teststraat"), closeSoftKeyboard())
        onView(withId(R.id.ev_house_number))
            .perform(typeText("12"), closeSoftKeyboard())
        onView(withId(R.id.ev_postal_code))
            .perform(typeText("1012AB"), closeSoftKeyboard())
        onView(withId(R.id.ev_city))
            .perform(typeText("Amsterdam"), closeSoftKeyboard())
        onView(withId(R.id.ev_country))
            .perform(click())
        onData(equalTo("NETHERLANDS"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
  }
}
