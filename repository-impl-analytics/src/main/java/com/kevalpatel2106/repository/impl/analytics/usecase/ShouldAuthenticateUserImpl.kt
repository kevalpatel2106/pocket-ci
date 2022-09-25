package com.kevalpatel2106.repository.impl.analytics.usecase

import android.app.Application
import android.provider.Settings
import com.kevalpatel2106.repository.di.IsDebug
import javax.inject.Inject

internal class ShouldAuthenticateUserImpl @Inject constructor(
    private val application: Application,
    @IsDebug private val isDebug: Boolean,
) : ShouldAuthenticateUser {

    override fun invoke() = !isDebug && !isFirebaseTestLabRunning()

    private fun isFirebaseTestLabRunning() =
        Settings.System.getString(application.contentResolver, "firebase.test.lab") == "true"
}
