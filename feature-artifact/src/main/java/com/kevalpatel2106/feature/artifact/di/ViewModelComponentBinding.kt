package com.kevalpatel2106.feature.artifact.di

import com.kevalpatel2106.feature.artifact.list.usecase.ArtifactItemMapper
import com.kevalpatel2106.feature.artifact.list.usecase.ArtifactItemMapperImpl
import com.kevalpatel2106.feature.artifact.usecase.ArtifactIconMapper
import com.kevalpatel2106.feature.artifact.usecase.ArtifactIconMapperImpl
import com.kevalpatel2106.feature.artifact.usecase.ArtifactSizeConverter
import com.kevalpatel2106.feature.artifact.usecase.ArtifactSizeConverterImpl
import com.kevalpatel2106.feature.artifact.usecase.DownloadFileFromUrl
import com.kevalpatel2106.feature.artifact.usecase.DownloadFileFromUrlImpl
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
    abstract fun bindArtifactIconMapper(impl: ArtifactIconMapperImpl): ArtifactIconMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactSizeConverter(impl: ArtifactSizeConverterImpl): ArtifactSizeConverter

    @Binds
    @ViewModelScoped
    abstract fun bindDownloadFileFromUrl(impl: DownloadFileFromUrlImpl): DownloadFileFromUrl

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactItemMapper(impl: ArtifactItemMapperImpl): ArtifactItemMapper
}
