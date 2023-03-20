package com.kevalpatel2106.feature.splash.di

import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashVMEventHandler
import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashVMEventHandlerImpl
import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashViewStatesEventHandler
import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashViewStatesEventHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal interface FragmentBinding {

    @Binds
    @FragmentScoped
    fun bindSplashVMEventHandler(impl: SplashVMEventHandlerImpl): SplashVMEventHandler

    @Binds
    @FragmentScoped
    fun bindSplashViewStatesEventHandler(
        impl: SplashViewStatesEventHandlerImpl
    ): SplashViewStatesEventHandler
}
