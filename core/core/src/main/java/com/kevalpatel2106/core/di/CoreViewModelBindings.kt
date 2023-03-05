package com.kevalpatel2106.core.di

import com.kevalpatel2106.core.usecase.SecondsTicker
import com.kevalpatel2106.core.usecase.SecondsTickerImpl
import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.core.usecase.TimeDifferenceFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface CoreViewModelBindings {

    @Binds
    fun bindTimeDifferenceFormatter(impl: TimeDifferenceFormatterImpl): TimeDifferenceFormatter

    @Binds
    fun bindLiveTimeDifferenceTicker(impl: SecondsTickerImpl): SecondsTicker
}
