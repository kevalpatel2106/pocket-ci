package com.kevalpatel2106.repositoryImpl.cache.db.crypto

import android.content.SharedPreferences
import androidx.core.content.edit
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.crypto.KeyGenerator
import javax.inject.Inject

internal class SqlcipherFactory @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences,
) {

    private fun generatePassphrase(): CharArray {
        val passphrase = KeyGenerator.getInstance(ALGORITHM_AES)
            .apply { init(KEY_SIZE) }
            .generateKey()
            .encoded
            .toString(Charsets.ISO_8859_1)
        encryptedSharedPreferences.edit(commit = true) {
            putString(DB_ENCRYPT_PREF_KEY, passphrase)
        }
        return passphrase.toCharArray()
    }

    private fun getPassphrase() = encryptedSharedPreferences
        .getString(DB_ENCRYPT_PREF_KEY, null)
        ?.toCharArray()

    fun create(): SupportFactory {
        val dbKey = getPassphrase() ?: generatePassphrase()
        return SupportFactory(SQLiteDatabase.getBytes(dbKey))
    }

    companion object {
        private const val DB_ENCRYPT_PREF_KEY = "DB_KEY"
        private const val ALGORITHM_AES = "AES"
        private const val KEY_SIZE = 256
    }
}
