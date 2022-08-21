package com.kevalpatel2106.coreTest.runner

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class UiTestsRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application? {
        return Instrumentation.newApplication(HiltTestApplication::class.java, context)
    }
}
