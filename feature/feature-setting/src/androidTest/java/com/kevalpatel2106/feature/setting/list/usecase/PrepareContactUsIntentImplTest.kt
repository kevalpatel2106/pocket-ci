package com.kevalpatel2106.feature.setting.list.usecase

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.resources.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class PrepareContactUsIntentImplTest {
    private val fixture = KFixture()
    private val versionCode = fixture<Int>()
    private val versionName = fixture<String>()
    private var subject = PrepareContactUsIntentImpl(getApplicationContext())

    @Test
    fun checkIntentChooserIsReturned() {
        val actual = subject(versionName, versionCode).action

        assertEquals(Intent.ACTION_CHOOSER, actual)
    }

    @Test
    fun checkIntentChooserTitle() {
        val actual = subject(versionName, versionCode).getStringExtra(Intent.EXTRA_TITLE)

        val expected = getApplicationContext<Application>()
            .getString(R.string.settings_contact_us_chooser_text)
        assertEquals(expected, actual)
    }

    @Test
    fun checkIntentActionIsSend() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.action

        assertEquals(Intent.ACTION_SEND, actual)
    }

    @Test
    fun checkIntentFlagsAreSetToOpenNewTask() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.flags

        val expected = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK
        assertEquals(expected, actual)
    }

    @Test
    fun checkIntentTextHasBuildInfo() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.getStringExtra(Intent.EXTRA_TEXT)

        assertTrue(actual?.contains(Build.MANUFACTURER) == true)
        assertTrue(actual?.contains(Build.DEVICE) == true)
        assertTrue(actual?.contains(Build.MODEL) == true)
        assertTrue(actual?.contains(Build.VERSION.SDK_INT.toString()) == true)
    }

    @Test
    fun checkIntentTextHasAppVersionInfo() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.getStringExtra(Intent.EXTRA_TEXT)

        assertTrue(actual?.contains(versionName) == true)
        assertTrue(actual?.contains(versionCode.toString()) == true)
    }

    @Test
    fun checkIntentSubjectHasAppName() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.getStringExtra(Intent.EXTRA_SUBJECT)

        val expectedName = getApplicationContext<Application>().getString(R.string.app_name)
        assertTrue(actual?.contains(expectedName) == true)
    }

    @Test
    fun checkSelectorIntentActionIsSendTo() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.selector?.action

        assertEquals(Intent.ACTION_SENDTO, actual)
    }

    @Test
    fun checkSelectorIntentSchemaIsMailTo() {
        val actual = subject(versionName, versionCode)
            .getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
            ?.selector?.data

        assertEquals(Uri.parse("mailto://"), actual)
    }
}
