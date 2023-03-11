package com.kevalpatel2106.feature.splash.di

import com.kevalpatel2106.feature.splash.usecase.HandleSplashVMEvents
import com.kevalpatel2106.feature.splash.usecase.HandleSplashVMEventsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal interface FragmentComponentBinding {

    @Binds
    @FragmentScoped
    fun bindHandleSplashVMEvents(impl: HandleSplashVMEventsImpl): HandleSplashVMEvents
}
