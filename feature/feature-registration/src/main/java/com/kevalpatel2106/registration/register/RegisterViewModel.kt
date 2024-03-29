package com.kevalpatel2106.registration.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.isUnAuthorized
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.entity.toUrl
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.ShowErrorAddingAccount
import com.kevalpatel2106.registration.register.model.RegisterViewState.Companion.initialState
import com.kevalpatel2106.registration.register.analytics.RegisterEvent
import com.kevalpatel2106.registration.register.model.RegisterVMEvent
import com.kevalpatel2106.registration.register.usecase.SanitiseRegisterInput
import com.kevalpatel2106.registration.register.usecase.ValidateRegisterInput
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.AnalyticsRepo
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
internal class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepo: AccountRepo,
    private val sanitiseRegisterInput: SanitiseRegisterInput,
    private val validateRegisterInput: ValidateRegisterInput,
    private val displayErrorMapper: DisplayErrorMapper,
    private val analyticsRepo: AnalyticsRepo,
) : ViewModel() {
    private val navArgs = RegisterFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(initialState(navArgs.selectedCI))
    val viewState = _viewState.asStateFlow()

    private val _vmEventsFlow = MutableSharedFlow<RegisterVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    fun onUrlChange() {
        _viewState.update { it.copy(urlErrorMsg = null) }
    }

    fun onTokenChange() {
        _viewState.update { it.copy(tokenErrorMsg = null) }
    }

    private fun validateInputs(url: String, token: String): Boolean {
        val (isValidUrl, isValidToken) = validateRegisterInput(url, token)
        val allGood = isValidUrl && isValidToken
        if (!allGood) {
            _viewState.update {
                it.copy(
                    tokenErrorMsg = if (!isValidToken) R.string.error_invalid_token_name else null,
                    urlErrorMsg = if (!isValidUrl) R.string.error_invalid_domain_name else null,
                )
            }
        }
        return allGood
    }

    fun submit(inputUrl: String, inputToken: String) {
        val (url, token) = sanitiseRegisterInput(inputUrl, inputToken)
        if (!validateInputs(url, token)) return

        viewModelScope.launch {
            _viewState.update { it.copy(enableAddAccountBtn = false) }

            runCatching {
                if (accountRepo.hasAccount(url.toUrl(), token.toToken())) {
                    AccountAlreadyAdded
                } else {
                    val savedAccount = accountRepo.addAccount(
                        ciType = navArgs.selectedCI.type,
                        url = url.toUrl(),
                        token = token.toToken(),
                    )
                    HandleAuthSuccess(savedAccount.localId, savedAccount.name)
                }
            }.onFailure { error ->
                if (!error.isUnAuthorized()) Timber.e(error)
                _vmEventsFlow.emit(ShowErrorAddingAccount(displayErrorMapper(error)))
                _viewState.update { it.copy(enableAddAccountBtn = true) }
            }.onSuccess {
                _vmEventsFlow.emit(it)
                when (it) {
                    AccountAlreadyAdded -> _viewState.update { it.copy(enableAddAccountBtn = true) }
                    is HandleAuthSuccess -> {
                        analyticsRepo.sendEvent(RegisterEvent(navArgs.selectedCI.type))
                    }

                    else -> Unit
                }
            }
        }
    }
}
