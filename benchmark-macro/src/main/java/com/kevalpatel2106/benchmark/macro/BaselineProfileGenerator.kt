package com.kevalpatel2106.benchmark.macro

import android.Manifest
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.kevalpatel2106.benchmark.macro.Const.PACKAGE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val baselineProfileRule = BaselineProfileRule()

    @get:Rule(order = 2)
    var permissionsRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    @Test
    fun generateBaselineProfile() {
        baselineProfileRule.collectBaselineProfile(packageName = PACKAGE) {
            // App startup journey
            startActivityAndWait()

            // Go to register screen
            device.findObject(By.text("Bitrise")).clickAndWait(Until.newWindow(), TIMEOUT_MS)
            device.pressBack()
        }
    }

    companion object {
        private const val TIMEOUT_MS = 1_000L
    }
}