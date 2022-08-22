package com.kevalpatel2106.feature.setting.di

import com.kevalpatel2106.feature.setting.list.usecase.ConvertPrefValueToNightMode
import com.kevalpatel2106.feature.setting.list.usecase.ConvertPrefValueToNightModeImpl
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapper
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapperImpl
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
    abstract fun bindConvertPrefValueToNightMode(
        impl: ConvertPrefValueToNightModeImpl,
    ): ConvertPrefValueToNightMode

    @Binds
    @ViewModelScoped
    abstract fun bindContentToUrlMapper(impl: ContentToUrlMapperImpl): ContentToUrlMapper
}
