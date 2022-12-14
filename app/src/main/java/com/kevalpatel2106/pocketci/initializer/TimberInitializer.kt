package com.kevalpatel2106.pocketci.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kevalpatel2106.repository.di.IsDebug
import dagger.hilt.InstallIn
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.EarlyEntryPoints
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Every Initializer must have a no argument constructor. So we cannot inject using hilt.
        // Hilt doesn't support out of the box content provider injection using @AndroidEntryPoint.
        val hiltEntryPoint: TimberInitializerEntryPoint = EarlyEntryPoints.get(
            context.applicationContext,
            TimberInitializerEntryPoint::class.java,
        )

        if (hiltEntryPoint.isDebug()) Timber.plant(Timber.DebugTree())
        Timber.plant(hiltEntryPoint.provideAnalyticsTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AnalyticsInitializer::class.java)
    }

    @EarlyEntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface TimberInitializerEntryPoint {

        fun provideAnalyticsTree(): TimberAnalyticsTree

        @IsDebug
        fun isDebug(): Boolean
    }
}
