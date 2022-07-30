package com.kevalpatel2106.coreNetwork.di

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkApiDebugModule {
    @Singleton
    @Provides
    fun provideNetworkFlipperPlugin() = NetworkFlipperPlugin()
}
