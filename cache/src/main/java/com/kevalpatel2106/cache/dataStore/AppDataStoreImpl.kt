package com.kevalpatel2106.cache.dataStore

import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.toAccountIdOrNull
import com.kevalpatel2106.cache.dataStore.AppDataStoreImpl.PreferencesKeys.SELECTED_ACCOUNT
import com.kevalpatel2106.cache.dataStore.AppDataStoreImpl.PreferencesKeys.SELECTED_NIGHT_MODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

internal class AppDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppDataStore {

    override suspend fun updateSelectedAccountId(selectedAccount: AccountId) {
        dataStore.edit { preferences -> preferences[SELECTED_ACCOUNT] = selectedAccount.getValue() }
    }

    override suspend fun getSelectedAccountId(): AccountId? = dataStore.data
        .first()[SELECTED_ACCOUNT]
        .toAccountIdOrNull()

    override fun observeSelectedAccountId(): Flow<AccountId?> = dataStore.data
        .catch { exception -> handleException(exception) }
        .map { it[SELECTED_ACCOUNT].toAccountIdOrNull() }

    override suspend fun updateNightMode(@AppCompatDelegate.NightMode selectedMode: Int) {
        dataStore.edit { preferences -> preferences[SELECTED_NIGHT_MODE] = selectedMode }
    }

    override suspend fun getNightMode(): Int {
        return dataStore.data.first()[SELECTED_NIGHT_MODE] ?: DEFAULT_NIGHT_MODE
    }

    override fun observeSelectedNightMode(): Flow<Int> = dataStore.data
        .catch { exception -> handleException(exception) }
        .map { it[SELECTED_NIGHT_MODE] ?: DEFAULT_NIGHT_MODE }

    private suspend fun FlowCollector<Preferences>.handleException(exception: Throwable) {
        if (exception is IOException) {
            // dataStore.data throws an IOException when an error is encountered when reading data
            Timber.e("Error reading preferences.", exception)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }

    @VisibleForTesting
    object PreferencesKeys {
        val SELECTED_ACCOUNT = longPreferencesKey("selected_account")
        val SELECTED_NIGHT_MODE = intPreferencesKey("selected_night_mode")
    }

    companion object {
        private const val DEFAULT_NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}
