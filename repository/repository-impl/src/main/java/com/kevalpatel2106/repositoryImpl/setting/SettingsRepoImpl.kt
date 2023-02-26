package com.kevalpatel2106.repositoryImpl.setting

import com.kevalpatel2106.cache.dataStore.AppDataStore
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.repository.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SettingsRepoImpl @Inject constructor(
    private val appDataStore: AppDataStore,
) : SettingsRepo {

    override fun observeNightMode(): Flow<NightMode> = appDataStore.observeSelectedNightMode()
        .map { mode -> NightMode.values().first { it.value == mode } }

    override suspend fun getNightMode(): NightMode {
        val value = appDataStore.getNightMode()
        return NightMode.values().first { it.value == value }
    }

    override suspend fun setNightMode(mode: NightMode) = appDataStore.updateNightMode(mode.value)
}
