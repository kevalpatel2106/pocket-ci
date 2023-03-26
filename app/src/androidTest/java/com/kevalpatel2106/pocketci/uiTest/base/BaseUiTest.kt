package com.kevalpatel2106.pocketci.uiTest.base

import android.os.Build
import androidx.test.rule.GrantPermissionRule
import com.kevalpatel2106.pocketci.screenshot.captureScreenshot
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName

internal abstract class BaseUiTest {

    @get:Rule(order = 0)
    var testNameRule: TestName = TestName()

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    val classTag: String
        get() = javaClass.simpleName

    @Before
    fun before() {
        captureScreenshot()
    }
}