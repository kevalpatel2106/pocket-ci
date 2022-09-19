package com.kevalpatel2106.repositoryImpl.di

import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.usecase.GetFirebaseRemoteConfigSettings
import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.usecase.GetFirebaseRemoteConfigSettingsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CacheSingletonComponentBinding {

    @Singleton
    @Binds
    abstract fun bindGetFirebaseRemoteConfigSettings(
        impl: GetFirebaseRemoteConfigSettingsImpl,
    ): GetFirebaseRemoteConfigSettings
}
