package com.kevalpatel2106.coreNetwork.di

import com.kevalpatel2106.coreNetwork.FlavouredInterceptorImpl
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactoryImpl
import com.kevalpatel2106.coreNetwork.usecase.IsNetworkConnectedCheck
import com.kevalpatel2106.coreNetwork.usecase.IsNetworkConnectedCheckImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkApiBindings {

    @Binds
    fun bindFlavouredInterceptors(impl: FlavouredInterceptorImpl): FlavouredInterceptor

    @Binds
    fun bindOkHttpClientFactory(impl: OkHttpClientFactoryImpl): OkHttpClientFactory

    @Binds
    fun bindIsNetworkConnected(impl: IsNetworkConnectedCheckImpl): IsNetworkConnectedCheck
}
