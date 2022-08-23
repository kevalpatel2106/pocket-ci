package com.kevalpatel2106.feature.setting.list.usecase

import android.app.Application
import android.content.Intent
import androidx.annotation.VisibleForTesting
import com.kevalpatel2106.feature.setting.R
import javax.inject.Inject

internal class PrepareAppInviteIntentImpl @Inject constructor(
    private val application: Application,
) : PrepareAppInviteIntent {
    private val appName by lazy { application.getString(R.string.app_name) }

    override operator fun invoke(): Intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = INTENT_TYPE_TEXT
        addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_NEW_TASK,
        )
        putExtra(
            Intent.EXTRA_TEXT,
            application.getString(R.string.settings_app_invite_message, appName, INVITATION_URL),
        )
        putExtra(
            Intent.EXTRA_SUBJECT,
            application.getString(R.string.settings_app_invite_title, appName),
        )
    }

    companion object {
        @VisibleForTesting
        const val INVITATION_URL = "https://pocketci.page.link/app-invite"
        private const val INTENT_TYPE_TEXT = "text/plain"
    }
}
