package com.kevalpatel2106.feature.log.di

import com.kevalpatel2106.feature.log.log.eventHandler.BuildLogVmEventHandler
import com.kevalpatel2106.feature.log.log.eventHandler.BuildLogVmEventHandlerImpl
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
    fun bindBuildLogVmEventHandler(impl: BuildLogVmEventHandlerImpl): BuildLogVmEventHandler

}
