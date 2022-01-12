package com.example.sharity_apk

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sharity_apk.adapter.ReservationAdapter
import com.example.sharity_apk.fragment.reservation.GetAllReservations


import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GetAllReservationsTest : TestFunctions() {

    /***
     * Recyclerview comes into view
     * */
    @Test
    fun test_isListFragmentVisible_onLaunch() {
        val scenario: FragmentScenario<GetAllReservations> =
            launchFragmentInContainer(themeResId = R.style.Theme_SharityAPK)
        scenario.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.recycler_view_reservation)).check(matches(isDisplayed()))
    }


    /***
     * Recyclerview comes into view, check different method
     * */
    @Test
    fun test_selectListItem_isFragmentVisible() {
        testLogin()

        onView(withText("MY RESERVATIONS"))
            .perform(click())

        onView(withId(R.id.tv_reservation_number)).check(matches(withText("Reservationnumber")))
    }

    /***
     * Select list item, nav to DetailFragment
     * Correct reservation is in view?
     * */
    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        testLogin()

        onView(withText("MY RESERVATIONS"))
            .perform(click())

        onView(withId(R.id.recycler_view_reservation))
            .perform(actionOnItemAtPosition<ReservationAdapter.ReservationViewHolder>(0, click()))

        onView(withId(R.id.title)).check(matches(withText("Reservation Details")))
    }


    /***
     * select list item, nav to DetailFragment
     * pressback
     * */
    @Test
    fun test_selectListItem_isFragmentVisible_atPressback() {
        testLogin()

        onView(withText("MY RESERVATIONS"))
            .perform(click())

        onView(withId(R.id.recycler_view_reservation))
            .perform(actionOnItemAtPosition<ReservationAdapter.ReservationViewHolder>(0, click()))

        onView(withId(R.id.title)).check(matches(withText("Reservation Details")))

        pressBack()

        onView(withId(R.id.recycler_view_reservation)).check((matches(isDisplayed())))

    }
}

