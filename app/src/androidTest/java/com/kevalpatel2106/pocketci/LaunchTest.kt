package com.kevalpatel2106.pocketci

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.pocketci.host.HostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
class LaunchTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityTestRule = ActivityTestRule(HostActivity::class.java, true, true)

    @Test
    fun checkHostActivityOpened() {
        onView(withText(R.string.title_ci_selection_screen)).check(matches(isDisplayed()))
    }
}