package com.kevalpatel2106.pocketci

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) = Timber.plant(Timber.DebugTree())
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
