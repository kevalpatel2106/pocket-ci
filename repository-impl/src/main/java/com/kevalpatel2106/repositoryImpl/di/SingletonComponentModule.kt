package com.kevalpatel2106.repositoryImpl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStoreImpl.Companion.appPreferenceDataStore
import com.kevalpatel2106.repositoryImpl.cache.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonComponentModule {

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext application: Context): DataStore<Preferences> {
        return application.appPreferenceDataStore
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context) =
        AppDb.create(application, inMemory = false)

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDb) = db.getAccountDao()

    @Provides
    @Singleton
    fun provideProjectDao(db: AppDb) = db.getProjectDao()
}
