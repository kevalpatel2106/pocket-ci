package com.kevalpatel2106.entity

import androidx.appcompat.app.AppCompatDelegate

enum class NightMode(@AppCompatDelegate.NightMode val value: Int) {
    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO),
    AUTO(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}
