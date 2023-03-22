package com.kevalpatel2106.pocketci.initializer

import android.content.Context
import androidx.startup.Initializer

/**
 * Stub for flipper initializer in release flavour
 */
internal class FlipperInitializer : Initializer<Unit> {
    override fun create(context: Context) = Unit
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
