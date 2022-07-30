package com.kevalpatel2106.settings.list.usecase

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.kevalpatel2106.settings.R
import javax.inject.Inject

internal class PrepareContactUsIntentImpl @Inject constructor(
    private val application: Application,
) : PrepareContactUsIntent {
    private val appName by lazy { application.getString(R.string.app_name) }

    override operator fun invoke(versionName: String, versionCode: Int): Intent {
        val emailTitle = "Query regarding $appName"
        val emailText = """"
        <ENTER YOUR MESSAGE HERE>
        ------------------------------
        PLEASE DON'T REMOVE/EDIT BELOW INFO:
        DEVICE NAME : ${Build.MODEL} (${Build.DEVICE})
        MANUFACTURER : ${Build.MANUFACTURER}
        ANDROID VERSION : ${Build.VERSION.SDK_INT}
        APPLICATION VERSION : $versionName ($versionCode)
        ------------------------------
        """.trimIndent()

        val emailIntent = Intent(Intent.ACTION_SENDTO)
            .apply {
                data = Uri.parse(EMAIL_URL_SCHEME)
                putExtra(Intent.EXTRA_EMAIL, SUPPORT_EMAIL)
                putExtra(Intent.EXTRA_SUBJECT, emailTitle)
                putExtra(Intent.EXTRA_TEXT, emailText)
                addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_NEW_TASK,
                )
            }

        return Intent.createChooser(
            emailIntent,
            application.getString(R.string.contact_us_chooser_text),
        )
    }

    companion object {
        private const val EMAIL_URL_SCHEME = "mailto://"
        private const val SUPPORT_EMAIL = "kevalpatel2106@gmail.com"
    }
}
