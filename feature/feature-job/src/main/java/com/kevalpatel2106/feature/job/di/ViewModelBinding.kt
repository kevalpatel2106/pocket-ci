package com.kevalpatel2106.feature.job.di

import com.kevalpatel2106.feature.job.list.usecase.JobItemMapper
import com.kevalpatel2106.feature.job.list.usecase.JobItemMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface ViewModelBinding {

    @Binds
    @ViewModelScoped
    fun bindJobItemMapper(impl: JobItemMapperImpl): JobItemMapper
}
