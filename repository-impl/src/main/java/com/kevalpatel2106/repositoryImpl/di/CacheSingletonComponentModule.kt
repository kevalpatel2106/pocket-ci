package com.kevalpatel2106.repositoryImpl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.MasterKeys
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.kevalpatel2106.repository.di.IsDebug
import com.kevalpatel2106.repositoryImpl.cache.db.AppDb
import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.usecase.GetFirebaseRemoteConfigSettings
import com.kevalpatel2106.repositoryImpl.cache.sharedPrefs.SharedPrefFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CacheSingletonComponentModule {
    private val Context.appPreferenceDataStore by preferencesDataStore(
        name = DATASTORE_PREFERENCES_NAME,
    )

    @Singleton
    @Provides
    @EnableEncryption
    fun provideEnableEncryption(@IsDebug isDebug: Boolean) = !isDebug

    @Provides
    @Singleton
    @MasterKey
    fun provideEncryptionMasterKey() = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext application: Context): DataStore<Preferences> {
        return application.appPreferenceDataStore
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(factory: SharedPrefFactory) = factory.create()

    @Provides
    @Singleton
    fun provideDatabase(factory: AppDb.Factory) = factory.create(inMemory = false)

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDb) = db.getAccountDao()

    @Provides
    @Singleton
    fun provideProjectDao(db: AppDb) = db.getProjectDao()

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(
        getConfigSettings: GetFirebaseRemoteConfigSettings,
    ): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
            .apply { setConfigSettingsAsync(getConfigSettings()) }
    }

    companion object {
        private const val DATASTORE_PREFERENCES_NAME = "app_preferences"
    }
}
