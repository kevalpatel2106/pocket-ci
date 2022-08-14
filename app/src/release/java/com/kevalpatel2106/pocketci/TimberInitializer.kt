package com.kevalpatel2106.pocketci

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Every Initializer must have a no argument constructor. So we cannot inject using hilt.
        // Hilt doesn't support out of the box content provider injection.
        val hiltEntryPoint: TimberInitializerEntryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            TimberInitializerEntryPoint::class.java,
        )
        Timber.plant(hiltEntryPoint.provideReleaseTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface TimberInitializerEntryPoint {
        fun provideReleaseTree(): ReleaseTree
    }
}
