package com.kevalpatel2106.feature.setting.list

internal sealed class SettingListVMEvent {
    object ErrorChangingTheme : SettingListVMEvent()
    data class OpenContactUs(val versionName: String, val versionCode: Int) : SettingListVMEvent()
    object OpenAppInvite : SettingListVMEvent()
    object OpenOpenSourceLicences : SettingListVMEvent()
    object OpenPrivacyPolicy : SettingListVMEvent()
    object OpenChangelog : SettingListVMEvent()
}
