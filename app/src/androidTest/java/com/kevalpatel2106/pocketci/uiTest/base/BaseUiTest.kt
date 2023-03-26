package com.kevalpatel2106.pocketci.uiTest.base

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule
import org.junit.rules.TestName

internal abstract class BaseUiTest {

    @get:Rule(order = 0)
    var testNameRule: TestName = TestName()

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)
}