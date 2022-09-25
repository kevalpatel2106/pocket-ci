package com.kevalpatel2106.cache.dataStore

import androidx.appcompat.app.AppCompatDelegate
import com.kevalpatel2106.entity.id.AccountId
import kotlinx.coroutines.flow.Flow

interface AppDataStore {

    suspend fun updateSelectedAccountId(selectedAccount: AccountId)

    suspend fun getSelectedAccountId(): AccountId?

    fun observeSelectedAccountId(): Flow<AccountId?>

    suspend fun updateNightMode(@AppCompatDelegate.NightMode selectedMode: Int)

    suspend fun getNightMode(): Int

    fun observeSelectedNightMode(): Flow<Int>
}
