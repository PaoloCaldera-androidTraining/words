package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/*  For testing navigation, NavController is forced to navigate, but without actually changing
    what is displayed in the device/emulator. Then NavController action is checked to be sure
    it reaches the correct destination.
 */

class NavigationTests {

    private lateinit var navController: NavController

    @Before
    fun launch_letterList_in_navigation() {

        // Get an instance of the nav controller
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Launch LetterListFragment in its container (any fragment can be selected)
        val letterListScenario =
            launchFragmentInContainer<LetterListFragment>(themeResId = R.style.Theme_Words)

        // Declare the elements used by the navController: the navigation graph and
        // the container view it acts on
        letterListScenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }
    }

    @Test
    fun navigate_to_words_nav_component() {

        // Click the item at position 2 (the letter C)
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )

        /*  Even if we performed a click, we won't see in the emulator any actual navigation.
            This is because the container is only aware of the launched fragment from
            "launchFragmentInContainer" method and not of all the others of the navigation pattern.
            Only the navController is aware of the entire navigation pattern since we give to it
            a reference of the navHost and of the navGraph.
         */

        // Check that the current nav controller destination is now the wordListFragment
        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }

}

/*  Annotations useful for android testings

    @Before     Code to be executed before each test method (@Test), which sets up the necessary
                variables and logics for more than one test
    @After      Code to be executed after each test method (@Test), to clean up resources or make
                the device return to a particular state

    @BeforeClass    Annotated also with @JvmStatic and defined in the companion object of the
                    class. It executes once, but the executed code applies to every method of the
                    class: it is useful to setup heavy resources and do expensive operations
    @AfterClass     Annotated also with @JvmStatic and defined in the companion object of the
                    class. It executes once, but the executed code applies to every method of the
                    class: it is useful to tear down heavy resources and do expensive operations
 */