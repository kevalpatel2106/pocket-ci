package com.kevalpatel2106.settings.list.usecase

import android.app.Application
import android.content.Intent
import com.kevalpatel2106.settings.R
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
        // TODO Replace with original url
        private const val INVITATION_URL = "http://app_invitation_url.com"
        private const val INTENT_TYPE_TEXT = "text/plain"
    }
}
