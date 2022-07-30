package com.kevalpatel2106.settings.list.usecase

import android.app.Application
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.settings.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class ConvertPrefValueToNightModeImplTest {

    private val application = mock<Application> {
        on { getString(R.string.pref_value_theme_dark) } doReturn THEME_DARK
        on { getString(R.string.pref_value_theme_light) } doReturn THEME_LIGHT
        on { getString(R.string.pref_value_theme_system_default) } doReturn THEME_AUTO
    }
    private val subject = ConvertPrefValueToNightModeImpl(application)

    @ParameterizedTest(name = "given pref value {0} when invoked then check expected night mode value is {1}")
    @MethodSource("provideValues")
    fun `given pref value when invoked then check expected night mode value`(
        prefValue: String?,
        expectedNightMode: NightMode?,
    ) {
        val result = subject.invoke(prefValue)

        assertEquals(expectedNightMode, result)
    }

    @Test
    fun `given pref value is invalid when invoked then exception thrown`() {
        val prefValue = fixture<String>()

        assertThrows<IllegalStateException> { subject.invoke(prefValue) }
    }

    companion object {
        private val fixture = KFixture()
        private const val THEME_DARK = "DARK"
        private const val THEME_LIGHT = "LIGHT"
        private const val THEME_AUTO = "AUTO"

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: pref value, expected night mode
            arguments(THEME_DARK, NightMode.ON),
            arguments(THEME_LIGHT, NightMode.OFF),
            arguments(THEME_AUTO, NightMode.AUTO),
            arguments(null, NightMode.AUTO),
        )
    }
}
