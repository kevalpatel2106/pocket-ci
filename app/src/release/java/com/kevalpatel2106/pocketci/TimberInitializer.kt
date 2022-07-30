package com.kevalpatel2106.pocketci

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) = Timber.plant(ReleaseTree())
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
