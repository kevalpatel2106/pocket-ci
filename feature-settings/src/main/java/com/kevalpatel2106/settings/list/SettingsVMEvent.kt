package com.kevalpatel2106.settings.list

internal sealed class SettingsVMEvent {
    object ErrorChangingTheme : SettingsVMEvent()
    data class OpenContactUs(val versionName: String, val versionCode: Int) : SettingsVMEvent()
    object OpenAppInvite : SettingsVMEvent()
    object OpenOpenSourceLicences : SettingsVMEvent()
    object OpenPrivacyPolicy : SettingsVMEvent()
    object OpenChangelog : SettingsVMEvent()
}
