package com.kevalpatel2106.coreViews.di

import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImage
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageImpl
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageTint
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageTintImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
internal interface ViewComponentBindings {

    @Binds
    fun bindGetBuildStatusImage(impl: GetBuildStatusImageImpl): GetBuildStatusImage

    @Binds
    fun bindGetBuildStatusImageTint(impl: GetBuildStatusImageTintImpl): GetBuildStatusImageTint
}
