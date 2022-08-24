package com.kevalpatel2106.coreNetwork.di

import com.kevalpatel2106.coreNetwork.FlavouredInterceptorImpl
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkApiBindings {

    @Binds
    abstract fun bindFlavouredInterceptors(impl: FlavouredInterceptorImpl): FlavouredInterceptor

    @Binds
    abstract fun bindOkHttpClientFactory(impl: OkHttpClientFactoryImpl): OkHttpClientFactory
}
