package com.kevalpatel2106.cache.di

import com.kevalpatel2106.cache.dataStore.AppDataStore
import com.kevalpatel2106.cache.dataStore.AppDataStoreImpl
import com.kevalpatel2106.cache.remoteConfig.FirebaseRemoteConfigCache
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigCache
import com.kevalpatel2106.cache.remoteConfig.usecase.GetFirebaseRemoteConfigSettings
import com.kevalpatel2106.cache.remoteConfig.usecase.GetFirebaseRemoteConfigSettingsImpl
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

    @Binds
    abstract fun bindAppDataStore(dataStore: AppDataStoreImpl): AppDataStore

    @Binds
    abstract fun bindAppRemoteConfig(impl: FirebaseRemoteConfigCache): RemoteConfigCache
}
