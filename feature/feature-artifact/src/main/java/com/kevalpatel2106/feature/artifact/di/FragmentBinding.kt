package com.kevalpatel2106.feature.artifact.di

import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListViewStateHandler
import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListViewStateHandlerImpl
import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListVmEventHandler
import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListVmEventHandlerImpl
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
    fun bindArtifactListVmEventHandler(
        impl: ArtifactListVmEventHandlerImpl
    ): ArtifactListVmEventHandler

    @Binds
    @FragmentScoped
    fun bindArtifactListViewStateHandler(
        impl: ArtifactListViewStateHandlerImpl
    ): ArtifactListViewStateHandler
}
