package com.kevalpatel2106.coreViews.di

import com.kevalpatel2106.coreViews.useCase.CalculateTickInterval
import com.kevalpatel2106.coreViews.useCase.CalculateTickIntervalImpl
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImage
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageImpl
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageTint
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageTintImpl
import com.kevalpatel2106.coreViews.useCase.LiveTimeDifferenceTicker
import com.kevalpatel2106.coreViews.useCase.LiveTimeDifferenceTickerImpl
import com.kevalpatel2106.coreViews.useCase.TimeDifferenceFormatter
import com.kevalpatel2106.coreViews.useCase.TimeDifferenceFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
internal interface ViewComponentBindings {

    @Binds
    fun bindCalculateTickInterval(impl: CalculateTickIntervalImpl): CalculateTickInterval

    @Binds
    fun bindTimeDifferenceFormatter(impl: TimeDifferenceFormatterImpl): TimeDifferenceFormatter

    @Binds
    fun bindLiveTimeDifferenceTicker(impl: LiveTimeDifferenceTickerImpl): LiveTimeDifferenceTicker

    @Binds
    fun bindGetBuildStatusImage(impl: GetBuildStatusImageImpl): GetBuildStatusImage

    @Binds
    fun bindGetBuildStatusImageTint(impl: GetBuildStatusImageTintImpl): GetBuildStatusImageTint
}
