package com.kevalpatel2106.pocketci.screenshot

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import com.kevalpatel2106.pocketci.uiTest.base.BaseUiTest
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

internal class ScreenshotCapture {
    private val simpleDateFormatter = SimpleDateFormat("HH-mm-ss")

    private fun getFormattedTimeStamp() = simpleDateFormatter.format(Date())

    fun captureScreenshot(
        testClass: String,
        testName: String,
        screenshotProperty: String? = null
    ) {
        val screenCapture = Screenshot.capture()
        val property = if (screenshotProperty.isNullOrBlank()) "" else ".$screenshotProperty"
        val name = "$testClass-$testName$property-${getFormattedTimeStamp()}.png"
        try {
            screenCapture.apply {
                this.name = name
                process(setOf(ScreenCaptureProcessor()))
            }
            Log.d(testClass, "Screenshot captured.")
        } catch (ex: IOException) {
            Log.e(testClass, "Could not take a screenshot", ex)
        }
    }

    inner class ScreenCaptureProcessor : BasicScreenCaptureProcessor() {

        private val externalDir by lazy {
            InstrumentationRegistry.getInstrumentation()
                .targetContext
                .getExternalFilesDir(null)!!
        }

        init {
            // Should match deviceScreenshotsDirectory in screenshot.gradle
            this.mDefaultScreenshotPath = File(externalDir.absolutePath, SCREENSHOTS_FOLDER_NAME)
        }

        override fun getFilename(prefix: String): String = prefix
    }

    companion object {
        const val SCREENSHOTS_FOLDER_NAME = "ui-test"
    }
}

internal fun BaseUiTest.captureScreenshot(screenshotProperty: String? = null) {
    ScreenshotCapture().captureScreenshot(
        testClass = classTag,
        testName = testNameRule.methodName,
        screenshotProperty = screenshotProperty,
    )
}