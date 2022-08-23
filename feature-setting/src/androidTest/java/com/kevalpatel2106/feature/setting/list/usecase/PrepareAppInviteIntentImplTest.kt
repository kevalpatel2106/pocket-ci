package com.kevalpatel2106.feature.setting.list.usecase

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kevalpatel2106.feature.setting.R
import com.kevalpatel2106.feature.setting.list.usecase.PrepareAppInviteIntentImpl.Companion.INVITATION_URL
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class PrepareAppInviteIntentImplTest {
    private var subject = PrepareAppInviteIntentImpl(getApplicationContext())

    @Test
    fun checkIntentActionIsSend() {
        val actual = subject().action

        assertEquals(Intent.ACTION_SEND, actual)
    }

    @Test
    fun checkIntentMimeTypeIsText() {
        val actual = subject().type

        assertEquals("text/plain", actual)
    }

    @Test
    fun checkIntentFlagsAreSetToOpenNewTask() {
        val actual = subject().flags

        val expected = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK
        assertEquals(expected, actual)
    }

    @Test
    fun checkIntentTextHasInvitationUrl() {
        val actual = subject().getStringExtra(Intent.EXTRA_TEXT)

        assertTrue(actual?.contains(INVITATION_URL) == true)
    }

    @Test
    fun checkIntentSubjectHasAppName() {
        val actual = subject().getStringExtra(Intent.EXTRA_SUBJECT)

        val expectedName = getApplicationContext<Application>().getString(R.string.app_name)
        assertTrue(actual?.contains(expectedName) == true)
    }
}
