package com.kevalpatel2106.feature.setting.list

import com.kevalpatel2106.entity.NightMode

internal data class SettingListViewState(
    val versionPreferenceSummary: String,
    val themeValue: NightMode,
) {

    companion object {
        fun initialState(appVersion: String, appVersionCode: Int) = SettingListViewState(
            versionPreferenceSummary = "$appVersion ($appVersionCode)",
            themeValue = NightMode.AUTO,
        )
    }
}
