package com.kevalpatel2106.feature.job.di

import com.kevalpatel2106.feature.job.list.eventHandler.JobListViewStateHandler
import com.kevalpatel2106.feature.job.list.eventHandler.JobListViewStateHandlerImpl
import com.kevalpatel2106.feature.job.list.eventHandler.JobListVmEventHandler
import com.kevalpatel2106.feature.job.list.eventHandler.JobListVmEventHandlerImpl
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
    fun bindJobListVmEventHandler(impl: JobListVmEventHandlerImpl): JobListVmEventHandler

    @Binds
    @FragmentScoped
    fun bindJobListViewStateHandler(impl: JobListViewStateHandlerImpl): JobListViewStateHandler
}
