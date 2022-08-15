package com.kevalpatel2106.coreNetwork.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
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
    fun provideOkHttpClient(
        connectivityManager: ConnectivityManager,
        flavouredInterceptor: FlavouredInterceptor,
    ) = OkHttpClientFactory(connectivityManager, flavouredInterceptor)

    @Singleton
    @Provides
    fun provideConnectivityManager(application: Application) =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
