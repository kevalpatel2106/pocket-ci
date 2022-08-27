package com.kevalpatel2106.entity.analytics

data class ClickEvent(val action: Action, val content: String? = null) : Event(Name.CLICK) {

    override val properties: Map<String, String?> = mapOf(
        PROPERTY_ACTION to action.value,
        PROPERTY_CONTENT to content,
    )

    enum class Action(val value: String) {
        // Settings
        SETTINGS_NIGHT_MODE_CHANGED(value = "settings_night_mode_changed"),
        SETTINGS_CONTACT_US_CLICKED(value = "settings_contact_us_clicked"),
        SETTINGS_SHARE_APP_CLICKED(value = "settings_share_app_clicked"),
        SETTINGS_OPEN_SOURCE_LICENCE_CLICKED(value = "settings_open_source_licence_clicked"),
        SETTINGS_SHOW_PRIVACY_POLICY_CLICKED(value = "settings_show_privacy_policy_clicked"),
        SETTINGS_CHANGELOG_CLICKED(value = "settings_changelog_clicked"),

        // Webview
        WEBVIEW_RELOAD(value = "webview_reload")
    }

    companion object {
        private const val PROPERTY_ACTION = "action"
        private const val PROPERTY_CONTENT = "content"
    }
}
