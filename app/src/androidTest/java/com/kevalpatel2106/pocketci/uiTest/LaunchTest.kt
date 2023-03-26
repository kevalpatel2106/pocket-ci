package com.kevalpatel2106.pocketci.uiTest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.pocketci.host.HostActivity
import com.kevalpatel2106.pocketci.screenshot.captureScreenshot
import com.kevalpatel2106.pocketci.uiTest.base.BaseUiTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
internal class LaunchTest : BaseUiTest() {

    @get:Rule(order = 1)
    var activityTestRule = ActivityScenarioRule(HostActivity::class.java)

    @Test
    fun checkHostActivityOpened() {
        captureScreenshot()
        Thread.sleep(1000)
        onView(withText(R.string.title_ci_selection_screen)).check(matches(isDisplayed()))
    }
}