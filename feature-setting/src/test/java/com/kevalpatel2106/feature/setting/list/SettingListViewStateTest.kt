package com.kevalpatel2106.feature.setting.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.NightMode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SettingListViewStateTest {
    private val fixture = KFixture()
    private val versionName = fixture<String>()
    private val versionCode = fixture<Int>()

    @Test
    fun `given app version when getting initial state then check version summary`() {
        val result = SettingListViewState.initialState(
            versionName,
            versionCode,
        ).versionPreferenceSummary

        val expected = "$versionName ($versionCode)"
        assertEquals(expected, result)
    }

    @Test
    fun `given app version when getting initial state then check theme value is auto`() {
        val result = SettingListViewState.initialState(versionName, versionCode).themeValue

        assertEquals(NightMode.AUTO, result)
    }
}
