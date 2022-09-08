package com.kevalpatel2106.pocketci.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kevalpatel2106.repository.AnalyticsRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class AnalyticsInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Every Initializer must have a no argument constructor. So we cannot inject using hilt.
        // Hilt doesn't support out of the box content provider injection using @AndroidEntryPoint.
        val hiltEntryPoint: AnalyticsInitializerEntryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            AnalyticsInitializerEntryPoint::class.java,
        )
        hiltEntryPoint.analyticsRepo().initialize()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface AnalyticsInitializerEntryPoint {
        fun analyticsRepo(): AnalyticsRepo
    }
}