package com.example.sharity_apk

import android.content.Context
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sharity_apk.config.SharityPreferences
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Test

open class TestFunctions {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLogin() {

        val preference = SharityPreferences(context)
        preference.clearPreferences()

//        Launch application
        launchActivity<MainActivity>()


//        Fill email en password
        onView(withId(R.id.login_email_text))
            .perform(click())
            .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.login_password_text))
            .perform(click())
            .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard())

//        Click login button
        onView(withId(R.id.button_login))
            .perform(click())
    }


//    Make a force click if exception on coordinates
    fun forceClick(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return Matchers.allOf(
                    ViewMatchers.isClickable(),
                    ViewMatchers.isEnabled(),
                    ViewMatchers.isDisplayed()
                )
            }
            override fun getDescription(): String {
                return "force click"
            }

            override fun perform(uiController: UiController, view: View) {
                view.performClick() // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}