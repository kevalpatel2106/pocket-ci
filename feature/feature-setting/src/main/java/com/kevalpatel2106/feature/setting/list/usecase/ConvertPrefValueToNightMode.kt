package com.kevalpatel2106.feature.setting.list.usecase

import com.kevalpatel2106.entity.NightMode

internal interface ConvertPrefValueToNightMode {
    operator fun invoke(prefValue: String?): NightMode
}
