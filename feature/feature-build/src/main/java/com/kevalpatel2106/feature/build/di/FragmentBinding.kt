package com.kevalpatel2106.feature.build.di

import com.kevalpatel2106.feature.build.detail.eventHandler.BuildDetailVmEventHandler
import com.kevalpatel2106.feature.build.detail.eventHandler.BuildDetailVmEventHandlerImpl
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListViewStateHandler
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListViewStateHandlerImpl
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListVmEventHandler
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListVmEventHandlerImpl
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
    fun bindBuildListVmEventHandler(impl: BuildListVmEventHandlerImpl): BuildListVmEventHandler

    @Binds
    @FragmentScoped
    fun bindBuildListViewStateHandler(
        impl: BuildListViewStateHandlerImpl
    ): BuildListViewStateHandler

    @Binds
    @FragmentScoped
    fun bindBuildDetailVmEventHandler(
        impl: BuildDetailVmEventHandlerImpl
    ): BuildDetailVmEventHandler
}
