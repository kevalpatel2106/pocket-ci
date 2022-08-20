package com.kevalpatel2106.pocketci.e2e

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.kevalpatel2106.pocketci.host.HostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
class LaunchTest {

    @get:Rule(order = 1)
    var activityTestRule = ActivityTestRule(HostActivity::class.java, true, true)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun checkHostActivityOpened() {
        onView(withId(com.kevalpatel2106.registration.R.id.ciListRecyclerView))
            .check(matches(isDisplayed()))
    }
}
