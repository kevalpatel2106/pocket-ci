package com.kevalpatel2106.feature.setting.list.usecase

import android.content.Intent

internal interface PrepareContactUsIntent {
    operator fun invoke(versionName: String, versionCode: Int): Intent
}
