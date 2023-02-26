package com.kevalpatel2106.feature.log.di

import com.kevalpatel2106.feature.log.usecase.CalculateTextScale
import com.kevalpatel2106.feature.log.usecase.CalculateTextScaleImpl
import com.kevalpatel2106.feature.log.usecase.ConvertToPaddedLogs
import com.kevalpatel2106.feature.log.usecase.ConvertToPaddedLogsImpl
import com.kevalpatel2106.feature.log.usecase.LogSourceSelector
import com.kevalpatel2106.feature.log.usecase.LogSourceSelectorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface ViewModelComponentBinding {

    @Binds
    @ViewModelScoped
    fun bindCalculateTextScale(impl: CalculateTextScaleImpl): CalculateTextScale

    @Binds
    @ViewModelScoped
    fun bindDownloadLog(impl: LogSourceSelectorImpl): LogSourceSelector

    @Binds
    @ViewModelScoped
    fun bindConvertToPaddedLogs(impl: ConvertToPaddedLogsImpl): ConvertToPaddedLogs
}
