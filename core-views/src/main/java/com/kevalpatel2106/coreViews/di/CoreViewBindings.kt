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
internal abstract class CoreViewBindings {

    @Binds
    abstract fun bindCalculateTickInterval(impl: CalculateTickIntervalImpl): CalculateTickInterval

    @Binds
    abstract fun bindTimeDifferenceFormatter(
        impl: TimeDifferenceFormatterImpl
    ): TimeDifferenceFormatter

    @Binds
    abstract fun bindLiveTimeDifferenceTicker(
        impl: LiveTimeDifferenceTickerImpl
    ): LiveTimeDifferenceTicker

    @Binds
    abstract fun bindGetBuildStatusImage(
        impl: GetBuildStatusImageImpl
    ): GetBuildStatusImage

    @Binds
    abstract fun bindGetBuildStatusImageTint(
        impl: GetBuildStatusImageTintImpl
    ): GetBuildStatusImageTint
}
