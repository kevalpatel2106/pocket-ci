package com.kevalpatel2106.feature.setting.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.entity.analytics.ClickEvent.Action
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.ErrorChangingTheme
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenAppInvite
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenChangelog
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenContactUs
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.feature.setting.list.model.SettingListViewState
import com.kevalpatel2106.feature.setting.list.usecase.ConvertPrefValueToNightMode
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SettingListViewModel @Inject constructor(
    private val settingsRepo: SettingsRepo,
    private val prefValueToNightMode: ConvertPrefValueToNightMode,
    private val appConfigRepo: AppConfigRepo,
    private val displayErrorMapper: DisplayErrorMapper,
    private val analyticsRepo: AnalyticsRepo,
) : ViewModel() {

    private val _vmEventsFlow = MutableSharedFlow<SettingListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(
        SettingListViewState.initialState(
            appVersion = appConfigRepo.getVersionName(),
            appVersionCode = appConfigRepo.getVersionCode(),
        ),
    )
    val viewState = _viewState.asStateFlow()

    init {
        observeNightMode()
    }

    private fun observeNightMode() {
        viewModelScope.launch {
            settingsRepo.observeNightMode()
                .distinctUntilChanged()
                .filter { it != _viewState.value.themeValue }
                .catch { error ->
                    Timber.e(error)
                    _vmEventsFlow.emit(ErrorChangingTheme(displayErrorMapper(error)))
                }
                .collectLatest { nightMode ->
                    _viewState.update { it.copy(themeValue = nightMode) }
                }
        }
    }

    fun onNightModeChanged(prefValue: String?) {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_NIGHT_MODE_CHANGED))
        viewModelScope.launch {
            settingsRepo.setNightMode(prefValueToNightMode(prefValue))
        }
    }

    fun onContactUsClicked() {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_CONTACT_US_CLICKED))
        viewModelScope.launch {
            _vmEventsFlow.emit(
                OpenContactUs(appConfigRepo.getVersionName(), appConfigRepo.getVersionCode()),
            )
        }
    }

    fun onShareAppClicked() {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_SHARE_APP_CLICKED))
        viewModelScope.launch { _vmEventsFlow.emit(OpenAppInvite) }
    }

    fun onShowOpenSourceLicencesClicked() {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_OPEN_SOURCE_LICENCE_CLICKED))
        viewModelScope.launch { _vmEventsFlow.emit(OpenOpenSourceLicences) }
    }

    fun onShowPrivacyPolicyClicked() {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_SHOW_PRIVACY_POLICY_CLICKED))
        viewModelScope.launch { _vmEventsFlow.emit(OpenPrivacyPolicy) }
    }

    fun onShowChangelogClicked() {
        analyticsRepo.sendEvent(ClickEvent(Action.SETTINGS_CHANGELOG_CLICKED))
        viewModelScope.launch { _vmEventsFlow.emit(OpenChangelog) }
    }
}
