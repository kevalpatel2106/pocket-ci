package com.kevalpatel2106.feature.artifacts.di

import com.kevalpatel2106.feature.artifacts.list.usecase.ArtifactIconMapper
import com.kevalpatel2106.feature.artifacts.list.usecase.ArtifactIconMapperImpl
import com.kevalpatel2106.feature.artifacts.list.usecase.ArtifactSizeConverter
import com.kevalpatel2106.feature.artifacts.list.usecase.ArtifactSizeConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ArtifactsModuleBinding {

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactIconMapper(impl: ArtifactIconMapperImpl): ArtifactIconMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactSizeConverter(impl: ArtifactSizeConverterImpl): ArtifactSizeConverter
}
