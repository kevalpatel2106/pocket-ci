package com.kevalpatel2106.feature.project.di

import com.kevalpatel2106.feature.project.list.eventHandler.ProjectListVMEventHandler
import com.kevalpatel2106.feature.project.list.eventHandler.ProjectListVMEventHandlerImpl
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
    fun bindProjectListVMEventHandler(impl: ProjectListVMEventHandlerImpl): ProjectListVMEventHandler

}
