package com.example.sharity_apk

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sharity_apk.config.SharityPreferences
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateAccountTest : TestFunctions() {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun createCustomerTest() {

        val preference = SharityPreferences(context)
        preference.clearPreferences()

        launchActivity<MainActivity>()

//        Click sign in button
        onView(withText("Create account"))
            .perform(forceClick())

//        Fill in the edittext
        onView(withHint("Email"))
            .perform(click())
            .perform(typeText("tester@sharity.com"), closeSoftKeyboard())
        onView(withHint("Password"))
            .perform(click())
            .perform(typeText("12312312"), closeSoftKeyboard())
        onView(withHint("Confirm password"))
            .perform(click())
            .perform(typeText("12312312"), closeSoftKeyboard())

//        click next button and go to Create Customer Fragment
        onView(withText("next"))
            .perform(click())

//        Fill in the edittext
        onView(withHint("First name"))
            .perform(click())
            .perform(typeText("Sharity"), closeSoftKeyboard())
        onView(withHint("Last name"))
            .perform(click())
            .perform(typeText("Tester"), closeSoftKeyboard())
        onView(withHint("Address"))
            .perform(click())
            .perform(typeText("teststraat"), closeSoftKeyboard())
        onView(withHint("Nr."))
            .perform(click())
            .perform(typeText("12"), closeSoftKeyboard())
        onView(withHint("PC"))
            .perform(click())
            .perform(typeText("1012AB"), closeSoftKeyboard())
        onView(withHint("City"))
            .perform(click())
            .perform(typeText("Amsterdam"), closeSoftKeyboard())
        onView(withHint("Country"))
            .perform(click())
        onData(equalTo("NETHERLANDS"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
        onView(withHint("Date of birth"))
            .perform(click())
        onView(withText("OK"))
            .perform(click())
        onView(withHint("Phone number"))
            .perform(click())
            .perform(typeText("0687553001"), closeSoftKeyboard())

//        click next button and go to Create Customer Fragment
        onView(withText("next"))
            .perform(click())

//        Fill in the edittext
        onView(withHint("License number"))
            .perform(click())
            .perform(typeText("265897445"), closeSoftKeyboard())
        onView(withHint("Valid until"))
            .perform(click())
        onView(withText("OK"))
            .perform(click())
        onView(withHint("Country"))
            .perform(click())
        onData(equalTo("BELGIUM"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        //        click next button and go to Create Customer Fragment
        onView(withText("next"))
            .perform(click())

        //        Fill in the edittext
        onView(withHint("IBAN"))
            .perform(click())
            .perform(typeText("NL98RABO198200923"), closeSoftKeyboard())
        onView(withHint("Name account holder"))
            .perform(click())
            .perform(typeText("S. Tester"), closeSoftKeyboard())
        onView(withId(R.id.btn_add_bankaccount))
            .check(matches(withText("START SHARING")))
  }

}

