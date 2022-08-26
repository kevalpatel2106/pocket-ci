package com.kevalpatel2106.repositoryImpl.cache.sharedPrefs

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flextrade.kfixture.KFixture
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class SharedPrefFactoryTest {
    private val fixture = KFixture()

    @Test
    fun given_encryption_disabled_when_created_then_unencrypted_preference_created() {
        val encryption = false
        val subject = SharedPrefFactory(
            ApplicationProvider.getApplicationContext(),
            fixture(),
            encryption,
        )

        val actual = subject.create()

        assertFalse(actual is EncryptedSharedPreferences)
    }
}
