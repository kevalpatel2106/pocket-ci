package com.kevalpatel2106.settings.list

import com.kevalpatel2106.entity.NightMode

internal data class SettingsViewState(
    val versionPreferenceSummary: String,
    val themeValue: NightMode,
) {

    companion object {
        fun initialState(appVersion: String, appVersionCode: Int): SettingsViewState {
            return SettingsViewState(
                versionPreferenceSummary = "$appVersion ($appVersionCode)",
                themeValue = NightMode.AUTO,
            )
        }
    }
}
