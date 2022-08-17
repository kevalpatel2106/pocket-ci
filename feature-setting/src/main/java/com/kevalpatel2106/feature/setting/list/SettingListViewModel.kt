package com.kevalpatel2106.feature.setting.list

import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.ErrorChangingTheme
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.OpenAppInvite
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.OpenChangelog
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.OpenContactUs
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.feature.setting.list.SettingListVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.feature.setting.list.usecase.ConvertPrefValueToNightMode
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
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
internal class SettingListViewModel @Inject constructor(
    private val settingsRepo: SettingsRepo,
    private val prefValueToNightMode: ConvertPrefValueToNightMode,
    private val appConfigRepo: AppConfigRepo,
) : BaseViewModel<SettingListVMEvent>() {

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
