package com.example.sharity_apk

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sharity_apk.adapter.ReservationAdapter

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GetAllReservationsTest : TestCase() {

//    @Rule
//    val activityTestRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


    private lateinit var scenario: FragmentScenario<GetAllReservations>

    @Before
    public override fun setUp() {

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_SharityAPK)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    /***
     * Recyclerview comes into view
     * */
    @Test
    fun test_isListFragmentVisible_onLaunch(){
        onView(withId(R.id.recycler_view_reservation)).check(matches(isDisplayed()))
    }


    /***
     * Select list item, nav to DetailFragment
     * Correct reservation is in view?
     * */
    @Test
    fun test_selectListItem_isDetailFragmentVisible(){
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<GetAllReservations>()

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)
//            navController.setCurrentDestination(R.id.action_GetAllReservations_to_GetReservationDetails)
            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.recycler_view_reservation))
           .perform(actionOnItemAtPosition<ReservationAdapter.ReservationViewHolder>(0,click()))

        onView(withId(R.id.recycler_view_reservation))
            .check(matches(withText("Reservationnumber")))

//        onView(withId(R.id.GetReservationDetails)).check(matches(withText("Reservation Details")))

    }

    /***
     * select list item, nav to DetailFragment
     * pressback
     * */



//    @Test
//    fun recycleViewTest(){
//         Espresso.onView((withId(R.id.recycler_view_reservation))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

//        var recyclerView :RecyclerView = activityRule.activity.findViewById(R.id.recycler_view_reservation)
//        var itemcount = recyclerView.adapter?.itemCount
//
//        if (itemcount != null)
//        {
//            Espresso.onView(withId(R.id.recycler_view_reservation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemcount.minus(1) ))
//        }
//
//        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_reservation)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
//        val nameitem : String ="Reservationnumber"
//        Espresso.onView(withText(nameitem)).check(matches(isDisplayed()))

//    }

    public override fun tearDown() {}
}