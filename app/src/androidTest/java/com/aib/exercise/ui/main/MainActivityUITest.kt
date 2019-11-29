@file:Suppress("DEPRECATION")

package com.aib.exercise.ui.main


import android.os.RemoteException
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.aib.exercise.R
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val LAUNCH_TIMEOUT = 5000L

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        // Initialize UiDevice instance
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        try {
            if (!device.isScreenOn) {
                device.wakeUp()
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

    }


    @Test
    fun allUIDataPresentTest() {

        onView(withId(R.id.list_forecast)).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            assertTrue(
                view is RecyclerView &&
                        view.adapter != null && view.adapter?.itemCount ?: -1 > 0
            )

        }
    }

    @Test
    @Throws(Exception::class)
    fun pullToRefreshTest() {
        onView(withId(R.id.layout_refresh)).perform(swipeDown())
    }


    @Suppress("unused")
    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
