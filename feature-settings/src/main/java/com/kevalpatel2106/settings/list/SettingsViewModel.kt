package com.kevalpatel2106.settings.list

import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
import com.kevalpatel2106.settings.list.SettingsVMEvent.ErrorChangingTheme
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenAppInvite
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenChangelog
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenContactUs
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.settings.list.SettingsVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.settings.list.usecase.ConvertPrefValueToNightMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsRepo: SettingsRepo,
    private val prefValueToNightMode: ConvertPrefValueToNightMode,
    private val appConfigRepo: AppConfigRepo,
) : BaseViewModel<SettingsVMEvent>() {

    private val _viewState = MutableStateFlow(
        SettingsViewState.initialState(
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
                .catch {
                    Timber.e(it)
                    _vmEventsFlow.emit(ErrorChangingTheme)
                }
                .collectLatest { _viewState.modify { copy(themeValue = it) } }
        }
    }

    fun onNightModeChanged(prefValue: String?) {
        viewModelScope.launch {
            settingsRepo.setNightMode(prefValueToNightMode(prefValue))
        }
    }

    fun onContactUsClicked() {
        viewModelScope.launch {
            _vmEventsFlow.emit(
                OpenContactUs(appConfigRepo.getVersionName(), appConfigRepo.getVersionCode()),
            )
        }
    }

    fun onShareAppClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenAppInvite) }
    }

    fun onShowOpenSourceLicencesClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenOpenSourceLicences) }
    }

    fun onShowPrivacyPolicyClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenPrivacyPolicy) }
    }

    fun onShowChangelogClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenChangelog) }
    }
}
