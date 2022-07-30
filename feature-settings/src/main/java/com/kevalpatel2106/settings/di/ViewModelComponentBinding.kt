package com.kevalpatel2106.settings.di

import com.kevalpatel2106.settings.list.usecase.ConvertPrefValueToNightMode
import com.kevalpatel2106.settings.list.usecase.ConvertPrefValueToNightModeImpl
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
}
