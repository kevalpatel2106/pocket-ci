package com.kevalpatel2106.pocketci

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
import com.kevalpatel2106.coreNetwork.BuildConfig
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import wtf.s1.android.thread.flipper.S1ThreadPlugin

internal class FlipperInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Every Initializer must have a no argument constructor. So we cannot inject using hilt.
        // Hilt doesn't support out of the box content provider injection.
        val hiltEntryPoint: FlipperInitializerEntryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            FlipperInitializerEntryPoint::class.java,
        )

        SoLoader.init(context, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            AndroidFlipperClient.getInstance(context).apply {
                addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
                addPlugin(SharedPreferencesFlipperPlugin(context))
                addPlugin(DatabasesFlipperPlugin(context))
                addPlugin(S1ThreadPlugin())
                addPlugin(hiltEntryPoint.networkFlipperPlugin())
            }.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface FlipperInitializerEntryPoint {
        fun networkFlipperPlugin(): NetworkFlipperPlugin
    }
}
