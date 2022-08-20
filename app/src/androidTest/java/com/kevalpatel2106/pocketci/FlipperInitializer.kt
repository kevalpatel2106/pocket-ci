package com.kevalpatel2106.pocketci

import android.content.Context
import androidx.startup.Initializer

internal class FlipperInitializer : Initializer<Unit> {
    override fun create(context: Context) = Unit
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
