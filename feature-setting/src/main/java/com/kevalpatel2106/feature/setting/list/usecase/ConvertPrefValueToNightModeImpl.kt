package com.kevalpatel2106.feature.setting.list.usecase

import android.app.Application
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.feature.setting.R
import javax.inject.Inject

internal class ConvertPrefValueToNightModeImpl @Inject constructor(
    private val application: Application,
) : ConvertPrefValueToNightMode {
    private val valueThemeDark by lazy { application.getString(R.string.pref_value_theme_dark) }
    private val valueThemeLight by lazy { application.getString(R.string.pref_value_theme_light) }
    private val valueThemeDefault by lazy {
        application.getString(R.string.pref_value_theme_system_default)
    }

    override operator fun invoke(prefValue: String?): NightMode = when (prefValue) {
        valueThemeDark -> NightMode.ON
        valueThemeLight -> NightMode.OFF
        valueThemeDefault -> NightMode.AUTO
        null -> NightMode.AUTO
        else -> error("Wrong pref value $prefValue")
    }
}
