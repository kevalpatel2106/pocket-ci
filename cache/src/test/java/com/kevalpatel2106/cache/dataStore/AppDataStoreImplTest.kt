package com.kevalpatel2106.cache.dataStore

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import app.cash.turbine.test
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.dataStore.AppDataStoreImpl.PreferencesKeys.SELECTED_ACCOUNT
import com.kevalpatel2106.cache.dataStore.AppDataStoreImpl.PreferencesKeys.SELECTED_NIGHT_MODE
import com.kevalpatel2106.coreTest.getAccountIdFixture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

internal class AppDataStoreImplTest {
    private val fixture = KFixture()
    private val dataStoreFlow = MutableStateFlow(emptyPreferences())
    private val dataStore = mock<DataStore<Preferences>> {
        on { data } doReturn dataStoreFlow
    }
    private val subject = AppDataStoreImpl(dataStore)

    @Test
    fun `given datastore update successful when updating selected account id then datastore preference updated`() =
        runTest {
            val selectedAccountId = getAccountIdFixture(fixture)
            whenever(dataStore.edit(any())).thenReturn(mock())

            subject.updateSelectedAccountId(selectedAccountId)

            verify(dataStore).updateData(any())
        }

    @Test
    fun `given selected account id stored when getting selected account id then correct account id returned`() =
        runTest {
            val selectedAccountId = getAccountIdFixture(fixture)
            dataStoreFlow.emit(
                emptyPreferences()
                    .toMutablePreferences()
                    .apply { this[SELECTED_ACCOUNT] = selectedAccountId.getValue() }
                    .toPreferences(),
            )

            val actual = subject.getSelectedAccountId()

            assertEquals(selectedAccountId, actual)
        }

    @Test
    fun `given no selected account id stored when getting selected account id then returns null`() =
        runTest {
            dataStoreFlow.emit(emptyPreferences())

            val actual = subject.getSelectedAccountId()

            assertNull(actual)
        }

    @Test
    fun `given selected account id changed when observing selected account id then verify new value emitted`() =
        runTest {
            val changedAccountId = getAccountIdFixture(fixture)
            dataStoreFlow.emit(
                emptyPreferences()
                    .toMutablePreferences()
                    .apply { this[SELECTED_ACCOUNT] = changedAccountId.getValue() }
                    .toPreferences(),
            )

            subject.observeSelectedAccountId().test {
                assertEquals(changedAccountId, awaitItem())
            }
        }

    @Test
    fun `given selected account id changed to null when observing selected account id then verify new value emitted`() =
        runTest {
            dataStoreFlow.emit(emptyPreferences())

            subject.observeSelectedAccountId().test {
                assertNull(awaitItem())
            }
        }

    @Test
    fun `given runtime error reading datastore when observing selected account id then error thrown`() =
        runTest {
            val dataStoreFlow = flow<Preferences> { throw IllegalArgumentException() }
            val dataStore = mock<DataStore<Preferences>> { on { data } doReturn dataStoreFlow }
            val subject = AppDataStoreImpl(dataStore)

            subject.observeSelectedAccountId().test {
                val error = awaitError()
                assertTrue(error is IllegalArgumentException)
            }
        }

    @Test
    fun `given IO error reading datastore when observing selected account id then null value returned`() =
        runTest {
            val dataStoreFlow = flow<Preferences> { throw IOException() }
            val dataStore = mock<DataStore<Preferences>> { on { data } doReturn dataStoreFlow }
            val subject = AppDataStoreImpl(dataStore)

            subject.observeSelectedAccountId().test {
                assertNull(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given datastore update successful when updating night mode then datastore preference updated`() =
        runTest {
            val selectedNightMode = MODE_NIGHT_NO
            whenever(dataStore.edit(any())).thenReturn(mock())

            subject.updateNightMode(selectedNightMode)

            verify(dataStore).updateData(any())
        }

    @Test
    fun `given selected night mode stored when getting night mode then correct night mode returned`() =
        runTest {
            val selectedNightMode = MODE_NIGHT_NO
            dataStoreFlow.emit(
                emptyPreferences()
                    .toMutablePreferences()
                    .apply { this[SELECTED_NIGHT_MODE] = selectedNightMode }
                    .toPreferences(),
            )

            val actual = subject.getNightMode()

            assertEquals(selectedNightMode, actual)
        }

    @Test
    fun `given no night mode stored when getting night mode then returns default night mode`() =
        runTest {
            dataStoreFlow.emit(emptyPreferences())

            val actual = subject.getNightMode()

            assertEquals(NIGHT_MODE_DEFAULT, actual)
        }

    @Test
    fun `given night mode stored changed when getting night mode then returns default night mode`() =
        runTest {
            dataStoreFlow.emit(
                emptyPreferences()
                    .toMutablePreferences()
                    .apply { this[SELECTED_NIGHT_MODE] = MODE_NIGHT_NO }
                    .toPreferences(),
            )
            dataStoreFlow.emit(
                emptyPreferences()
                    .toMutablePreferences()
                    .apply { this[SELECTED_NIGHT_MODE] = MODE_NIGHT_YES }
                    .toPreferences(),
            )

            subject.observeSelectedNightMode().test {
                assertEquals(MODE_NIGHT_YES, awaitItem())
            }
        }

    @Test
    fun `given night mode changed to null when observing night mode then verify default night mode emitted`() =
        runTest {
            dataStoreFlow.emit(emptyPreferences())

            subject.observeSelectedNightMode().test {
                assertEquals(NIGHT_MODE_DEFAULT, awaitItem())
            }
        }

    @Test
    fun `given runtime error reading datastore when observing night mode then error thrown`() =
        runTest {
            val dataStoreFlow = flow<Preferences> { throw IllegalArgumentException() }
            val dataStore = mock<DataStore<Preferences>> { on { data } doReturn dataStoreFlow }
            val subject = AppDataStoreImpl(dataStore)

            subject.observeSelectedNightMode().test {
                val error = awaitError()
                assertTrue(error is IllegalArgumentException)
            }
        }

    @Test
    fun `given IO error reading datastore when observing night mode then default night mode returned`() =
        runTest {
            val dataStoreFlow = flow<Preferences> { throw IOException() }
            val dataStore = mock<DataStore<Preferences>> { on { data } doReturn dataStoreFlow }
            val subject = AppDataStoreImpl(dataStore)

            subject.observeSelectedNightMode().test {
                assertEquals(NIGHT_MODE_DEFAULT, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    companion object {
        private const val NIGHT_MODE_DEFAULT = MODE_NIGHT_FOLLOW_SYSTEM
    }
}
