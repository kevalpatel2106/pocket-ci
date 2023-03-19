package com.kevalpatel2106.feature.setting.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_CHANGELOG_CLICKED
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_CONTACT_US_CLICKED
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_NIGHT_MODE_CHANGED
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_OPEN_SOURCE_LICENCE_CLICKED
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_SHARE_APP_CLICKED
import com.kevalpatel2106.entity.analytics.ClickEvent.Action.SETTINGS_SHOW_PRIVACY_POLICY_CLICKED
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.ErrorChangingTheme
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenAppInvite
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenChangelog
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenContactUs
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.feature.setting.list.usecase.ConvertPrefValueToNightMode
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
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
internal class SettingListViewModelTest {
    private val fixture = KFixture()
    private val displayError = fixture<DisplayError>()
    private val displayErrorMapper = mock<DisplayErrorMapper> {
        on { invoke(any(), any(), any()) } doReturn displayError
    }
    private val versionName = fixture<String>()
    private val versionCode = fixture<Int>()
    private val prefValueToNightMode = mock<ConvertPrefValueToNightMode>()
    private val settingsRepo = mock<SettingsRepo> {
        on { observeNightMode() } doReturn emptyFlow()
    }
    private val analyticsRepo = mock<AnalyticsRepo>()
    private val appConfigRepo = mock<AppConfigRepo> {
        on { getVersionCode() } doReturn versionCode
        on { getVersionName() } doReturn versionName
    }
    private val subject by lazy {
        SettingListViewModel(
            settingsRepo,
            prefValueToNightMode,
            appConfigRepo,
            displayErrorMapper,
            analyticsRepo,
        )
    }

    @Test
    fun `given version code and name when vm initialised then version code and name displayed in version info`() =
        runTest {
            val subject = SettingListViewModel(
                settingsRepo,
                prefValueToNightMode,
                appConfigRepo,
                displayErrorMapper,
                analyticsRepo,
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

            val subject = SettingListViewModel(
                settingsRepo,
                prefValueToNightMode,
                appConfigRepo,
                displayErrorMapper,
                analyticsRepo,
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
            assertEquals(ErrorChangingTheme(displayError), flowTurbine.awaitItem())
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
    fun `when nigh mode changed click event logged`() {
        subject.onNightModeChanged(fixture())

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_NIGHT_MODE_CHANGED))
    }

    @Test
    fun `when contact us clicked then contact us screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onContactUsClicked()

            assertEquals(
                OpenContactUs(versionName, versionCode),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `when contact us clicked then click event logged`() {
        subject.onContactUsClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_CONTACT_US_CLICKED))
    }

    @Test
    fun `when share app clicked then app invite chooser opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShareAppClicked()

            assertEquals(OpenAppInvite, flowTurbine.awaitItem())
        }

    @Test
    fun `when share app clicked then click event logged`() {
        subject.onShareAppClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_SHARE_APP_CLICKED))
    }

    @Test
    fun `when show open source licences clicked then opensource licences list opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowOpenSourceLicencesClicked()

            assertEquals(OpenOpenSourceLicences, flowTurbine.awaitItem())
        }

    @Test
    fun `when show open source licences clicked then click event logged`() {
        subject.onShowOpenSourceLicencesClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_OPEN_SOURCE_LICENCE_CLICKED))
    }

    @Test
    fun `when show privacy policy clicked then privacy policy opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowPrivacyPolicyClicked()

            assertEquals(OpenPrivacyPolicy, flowTurbine.awaitItem())
        }

    @Test
    fun `when show privacy policy clicked then click event logged`() {
        subject.onShowPrivacyPolicyClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_SHOW_PRIVACY_POLICY_CLICKED))
    }

    @Test
    fun `when show changelog clicked then changelog opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onShowChangelogClicked()

            assertEquals(OpenChangelog, flowTurbine.awaitItem())
        }

    @Test
    fun `when show changelog clicked then click event logged`() {
        subject.onShowChangelogClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(SETTINGS_CHANGELOG_CLICKED))
    }
}
