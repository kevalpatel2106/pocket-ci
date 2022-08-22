package com.kevalpatel2106.repositoryImpl.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.kevalpatel2106.repositoryImpl.cache.db.AppDb
import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.FirebaseRemoteConfigCache
import com.kevalpatel2106.repositoryImpl.cache.sharedPrefs.SharedPrefFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.UUID
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CacheSingletonComponentModule::class],
)
internal class InstrumentedCacheSingletonComponent {
    private val Context.appPreferenceDataStore by preferencesDataStore(
        name = UUID.randomUUID().toString(), // Create new DataStore for each test
    )

    @Singleton
    @Provides
    @EnableEncryption
    fun provideEnableEncryption() = false

    @Provides
    @Singleton
    @MasterKey
    fun provideEncryptionMasterKey() = UUID.randomUUID().toString()

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext application: Context): DataStore<Preferences> {
        return application.appPreferenceDataStore
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(factory: SharedPrefFactory): SharedPreferences {
        return factory.create(name = UUID.randomUUID().toString()) // New preference for each test
    }

    @Provides
    @Singleton
    fun provideDatabase(factory: AppDb.Factory) = factory.create(inMemory = true)

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDb) = db.getAccountDao()

    @Provides
    @Singleton
    fun provideProjectDao(db: AppDb) = db.getProjectDao()

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfigCache.Factory(isDebug = false).create()
    }
}
