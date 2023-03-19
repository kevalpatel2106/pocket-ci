package com.kevalpatel2106.feature.setting.list.model

import com.kevalpatel2106.entity.DisplayError

internal sealed class SettingListVMEvent {
    data class ErrorChangingTheme(val error: DisplayError) : SettingListVMEvent()
    data class OpenContactUs(val versionName: String, val versionCode: Int) : SettingListVMEvent()
    object OpenAppInvite : SettingListVMEvent()
    object OpenOpenSourceLicences : SettingListVMEvent()
    object OpenPrivacyPolicy : SettingListVMEvent()
    object OpenChangelog : SettingListVMEvent()
}
