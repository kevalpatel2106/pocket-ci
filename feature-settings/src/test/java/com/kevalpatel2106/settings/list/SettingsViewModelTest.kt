package com.kevalpatel2106.settings.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
import com.kevalpatel2106.settings.list.SettingsVMEvent.ErrorChangingTheme
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenAppInvite
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenChangelog
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenContactUs
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.settings.list.usecase.ConvertPrefValueToNightMode
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
class SettingsViewModelTest {
    private val fixture = KFixture()
    private val versionName = fixture<String>()
    private val versionCode = fixture<Int>()
    private val prefValueToNightMode = mock<ConvertPrefValueToNightMode>()
    private val settingsRepo = mock<SettingsRepo> {
        on { observeNightMode() } doReturn emptyFlow()
    }
    private val appConfigRepo = mock<AppConfigRepo> {
        on { getVersionCode() } doReturn versionCode
        on { getVersionName() } doReturn versionName
    }
    private val subject by lazy {
        SettingsViewModel(
            settingsRepo,
            prefValueToNightMode,
            appConfigRepo,
        )
    }

    @Test
    fun `given version code and name when vm initialised then version code and name displayed in version info`() =
        runTest {
            val subject = SettingsViewModel(
                settingsRepo,
                prefValueToNightMode,
                appConfigRepo,
            )
            advanceUntilIdle()

            val expected = "$versionName ($versionCode)"
            assertEquals(expected, subject.viewState.value.versionPreferenceSummary)
        }

    @Test
    fun `given night mode set to auto when nigh mode changed to on then view state changed`() =
        runTest {
            val nightModeFlow = flow { emit(NightMode.ON) }
            whenever(settingsRepo.observeNightMode()).thenReturn(nightModeFlow)

            val subject = SettingsViewModel(
                settingsRepo,
                prefValueToNightMode,
                appConfigRepo,
            )
            advanceUntilIdle()

            assertEquals(NightMode.ON, subject.viewState.value.themeValue)
        }

    @Test
    fun `given night mode set to on when error occurred nigh mode changed to on then error message displayed`() {
        runTest {
            val nightModeFlow = flow {
                emit(NightMode.ON)
                error("Tests")
            }
            whenever(settingsRepo.observeNightMode()).thenReturn(nightModeFlow)
        }
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            testScope.advanceUntilIdle()
            assertEquals(ErrorChangingTheme, flowTurbine.awaitItem())
        }
    }

    @Test
    fun `given new night mode when nigh mode changed verify new night mode saved`() = runTest {
        val newNightMode = NightMode.OFF
        whenever(prefValueToNightMode.invoke(any())).thenReturn(newNightMode)

        subject.onNightModeChanged(fixture())
        advanceUntilIdle()

        verify(settingsRepo).setNightMode(newNightMode)
    }

    @Test
    fun `given app version and code when contact us clicked then contact us screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onContactUsClicked()

            assertEquals(
                OpenContactUs(versionName, versionCode),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `when share app clicked then app invite chooser opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShareAppClicked()

            assertEquals(OpenAppInvite, flowTurbine.awaitItem())
        }

    @Test
    fun `when show open source licences clicked then opensource licences list opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowOpenSourceLicencesClicked()

            assertEquals(OpenOpenSourceLicences, flowTurbine.awaitItem())
        }

    @Test
    fun `when show privacy policy clicked then privacy policy opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowPrivacyPolicyClicked()

            assertEquals(OpenPrivacyPolicy, flowTurbine.awaitItem())
        }

    @Test
    fun `when show changelog clicked then changelog opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowChangelogClicked()

            assertEquals(OpenChangelog, flowTurbine.awaitItem())
        }
}
