package com.kevalpatel2106.cache.sharedPrefs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.kevalpatel2106.cache.di.EnableEncryption
import com.kevalpatel2106.cache.di.MasterKey
import javax.inject.Inject

internal class SharedPrefFactory @Inject constructor(
    private val application: Application,
    @MasterKey private val masterKey: String,
    @EnableEncryption private val encryptionEnabled: Boolean,
) {

    fun create(name: String = SHARED_PREFERENCES_NAME): SharedPreferences = if (encryptionEnabled) {
        EncryptedSharedPreferences.create(
            name,
            masterKey,
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    } else {
        application.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "pocket-ci-pref"
    }
}
