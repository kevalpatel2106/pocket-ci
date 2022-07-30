package com.kevalpatel2106.repository

import com.kevalpatel2106.entity.NightMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {

    fun observeNightMode(): Flow<NightMode>

    suspend fun getNightMode(): NightMode

    suspend fun setNightMode(mode: NightMode)
}
