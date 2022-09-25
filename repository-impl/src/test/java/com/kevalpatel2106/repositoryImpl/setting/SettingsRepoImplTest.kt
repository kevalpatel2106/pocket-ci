package com.kevalpatel2106.repositoryImpl.setting

import app.cash.turbine.test
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.cache.dataStore.AppDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class SettingsRepoImplTest {
    private val nightModeDataStoreFlow = MutableStateFlow<Int>(NightMode.ON.value)
    private val dataStore = mock<AppDataStore> {
        on { observeSelectedNightMode() } doReturn nightModeDataStoreFlow
    }
    private val subject = SettingsRepoImpl(dataStore)

    @ParameterizedTest(name = "given night mode {0} to save when set night mode called check value stored in datastore")
    @EnumSource(NightMode::class)
    fun `given night mode to save when set night mode called check value stored in datastore`(
        input: NightMode,
    ) = runTest {
        subject.setNightMode(input)

        verify(dataStore).updateNightMode(input.value)
    }

    @ParameterizedTest(name = "given night mode {0} saved when get night mode called check value")
    @EnumSource(NightMode::class)
    fun `given night mode saved when get night mode called check value`(nightModeSaved: NightMode) =
        runTest {
            whenever(dataStore.getNightMode()).thenReturn(nightModeSaved.value)

            val actual = subject.getNightMode()

            assertEquals(nightModeSaved, actual)
        }

    @ParameterizedTest(name = "given night mode changed to {0} when observing night mode then verify new value emitted")
    @EnumSource(NightMode::class)
    fun `given night mode changed when observing night mode then verify new value emitted`(
        newNightMode: NightMode,
    ) = runTest {
        nightModeDataStoreFlow.emit(newNightMode.value)

        subject.observeNightMode().test {
            assertEquals(newNightMode, awaitItem())
        }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = CIType.values().map { arrayOf(it.id, it) }
    }
}
