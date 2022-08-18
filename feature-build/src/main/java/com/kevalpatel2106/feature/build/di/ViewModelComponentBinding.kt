package com.kevalpatel2106.feature.build.di

import com.kevalpatel2106.feature.build.list.usecase.BuildItemMapper
import com.kevalpatel2106.feature.build.list.usecase.BuildItemMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ViewModelComponentBinding {

    @Binds
    @ViewModelScoped
    abstract fun bindBuildItemMapper(impl: BuildItemMapperImpl): BuildItemMapper
}
