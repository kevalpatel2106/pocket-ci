package com.kevalpatel2106.feature.logs.di

import com.kevalpatel2106.feature.logs.usecase.CalculateTextScale
import com.kevalpatel2106.feature.logs.usecase.CalculateTextScaleImpl
import com.kevalpatel2106.feature.logs.usecase.LogSourceSelector
import com.kevalpatel2106.feature.logs.usecase.LogSourceSelectorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class LogsModuleBindings {

    @Binds
    @ViewModelScoped
    abstract fun bindCalculateTextScale(impl: CalculateTextScaleImpl): CalculateTextScale

    @Binds
    @ViewModelScoped
    abstract fun bindDownloadLog(impl: LogSourceSelectorImpl): LogSourceSelector
}
