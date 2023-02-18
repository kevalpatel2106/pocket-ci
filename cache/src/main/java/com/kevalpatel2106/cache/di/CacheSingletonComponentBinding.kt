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
internal interface CacheSingletonComponentBinding {

    @Singleton
    @Binds
    fun bindGetFirebaseRemoteConfigSettings(
        impl: GetFirebaseRemoteConfigSettingsImpl,
    ): GetFirebaseRemoteConfigSettings

    @Binds
    fun bindAppDataStore(dataStore: AppDataStoreImpl): AppDataStore

    @Binds
    fun bindAppRemoteConfig(impl: FirebaseRemoteConfigCache): RemoteConfigCache
}
