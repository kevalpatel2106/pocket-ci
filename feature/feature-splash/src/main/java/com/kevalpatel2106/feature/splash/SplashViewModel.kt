package com.kevalpatel2106.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.feature.splash.SplashVMEvent.OpenProjects
import com.kevalpatel2106.feature.splash.SplashVMEvent.OpenRegisterAccount
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val accountRepo: AccountRepo,
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    private val _vmEventsFlow = MutableSharedFlow<SplashVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(SplashViewState.initialState())
    val viewState = _viewState.asStateFlow()

    init {
        observeNightMode()
    }

    private fun observeNightMode() {
        viewModelScope.launch {
            runCatching { settingsRepo.getNightMode() }
                .onFailure { error ->
                    Timber.e(error)
                    _viewState.update { it.copy(nightMode = NightMode.AUTO) }
                    _vmEventsFlow.emit(SplashVMEvent.ErrorLoadingTheme)
                }
                .onSuccess { nightMode ->
                    _viewState.update { it.copy(nightMode = nightMode) }
                }
            navigate()
        }
    }

    private fun navigate() {
        viewModelScope.launch {
            runCatching {
                val hasAnyAccount = accountRepo.hasAnyAccount()
                if (hasAnyAccount) {
                    accountRepo.getSelectedAccount()
                } else {
                    NO_ACCOUNT
                }
            }.onFailure { error ->
                Timber.e(error)
                _vmEventsFlow.emit(SplashVMEvent.CloseApplication)
            }.onSuccess { selectedAccount ->
                _vmEventsFlow.emit(
                    if (selectedAccount == NO_ACCOUNT) {
                        OpenRegisterAccount
                    } else {
                        OpenProjects(selectedAccount.localId)
                    },
                )
            }
        }
    }

    companion object {
        private val NO_ACCOUNT = null
    }
}
