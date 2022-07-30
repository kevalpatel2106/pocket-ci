package com.kevalpatel2106.coreNetwork.di

import com.kevalpatel2106.coreNetwork.moshi.MoshiFactory
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkApiSingletonModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClientFactory()

    @Singleton
    @Provides
    fun provideMoshiFactory() = MoshiFactory()
}
