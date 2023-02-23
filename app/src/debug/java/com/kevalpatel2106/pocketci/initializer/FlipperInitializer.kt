package com.kevalpatel2106.pocketci.initializer

import android.content.Context
import androidx.startup.Initializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.InstallIn
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.EarlyEntryPoints
import dagger.hilt.components.SingletonComponent

internal class FlipperInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Every Initializer must have a no argument constructor. So we cannot inject using hilt.
        // Hilt doesn't support out of the box content provider injection using @AndroidEntryPoint.
        val hiltEntryPoint: FlipperInitializerEntryPoint = EarlyEntryPoints.get(
            context.applicationContext,
            FlipperInitializerEntryPoint::class.java,
        )

        SoLoader.init(context, false)
        if (FlipperUtils.shouldEnableFlipper(context)) {
            AndroidFlipperClient.getInstance(context).apply {
                addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
                addPlugin(SharedPreferencesFlipperPlugin(context))
                addPlugin(DatabasesFlipperPlugin(context))
                addPlugin(hiltEntryPoint.networkFlipperPlugin())
            }.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(TimberInitializer::class.java)

    @EarlyEntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface FlipperInitializerEntryPoint {
        fun networkFlipperPlugin(): NetworkFlipperPlugin
    }
}
