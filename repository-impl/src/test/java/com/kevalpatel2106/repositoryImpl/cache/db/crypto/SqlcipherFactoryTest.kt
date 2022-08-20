package com.kevalpatel2106.repositoryImpl.cache.db.crypto

import android.content.SharedPreferences
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.repositoryImpl.cache.db.crypto.SqlcipherFactory.Companion.DB_ENCRYPT_PREF_KEY
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SqlcipherFactoryTest {
    private val fixture = KFixture()
    private val sharedPrefsEditor = mock<SharedPreferences.Editor>()
    private val sharedPrefs = mock<SharedPreferences> {
        on { edit() } doReturn sharedPrefsEditor
    }

    private val subject = SqlcipherFactory(sharedPrefs)

    @Test
    fun `given no passphrase saved in shared prefs when created then verify new passphrase saved to shared preference`() {
        whenever(sharedPrefs.getString(DB_ENCRYPT_PREF_KEY, null)).thenReturn(null)

        subject.create()

        verify(sharedPrefsEditor).putString(eq(DB_ENCRYPT_PREF_KEY), anyOrNull())
    }

    @Test
    fun `given passphrase saved in shared prefs when created then verify passphrase read from shared preference`() {
        val passphrase = fixture<String>()
        whenever(sharedPrefs.getString(DB_ENCRYPT_PREF_KEY, null)).thenReturn(passphrase)

        subject.create()

        verify(sharedPrefs).getString(eq(DB_ENCRYPT_PREF_KEY), eq(null))
    }
}
