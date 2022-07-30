package com.kevalpatel2106.registration.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.entity.toUrl
import com.kevalpatel2106.registration.R
import com.kevalpatel2106.registration.register.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.RegisterVMEvent.ShowErrorAddingAccount
import com.kevalpatel2106.registration.register.RegisterViewState.Companion.initialState
import com.kevalpatel2106.registration.register.usecase.SanitiseRegisterInput
import com.kevalpatel2106.registration.register.usecase.ValidateRegisterInput
import com.kevalpatel2106.repository.AccountRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepo: AccountRepo,
    private val sanitiseRegisterInput: SanitiseRegisterInput,
    private val validateRegisterInput: ValidateRegisterInput,
) : BaseViewModel<RegisterVMEvent>() {
    private val navArgs = RegisterFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(initialState(navArgs.selectedCI))
    val viewState = _viewState.asStateFlow()

    private fun validateInputs(url: String, token: String): Boolean {
        val (isValidUrl, isValidToken) = validateRegisterInput(url, token)
        val allGood = isValidUrl && isValidToken
        if (!allGood) {
            _viewState.modify(viewModelScope) {
                copy(
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

        _viewState.modify(viewModelScope) { copy(enableAddAccountBtn = false) }

        viewModelScope.launch {
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
                Timber.e(error)
                _vmEventsFlow.emit(ShowErrorAddingAccount)
                _viewState.modify { copy(enableAddAccountBtn = true) }
            }.onSuccess {
                _vmEventsFlow.emit(it)
                if (it == AccountAlreadyAdded) {
                    _viewState.modify { copy(enableAddAccountBtn = true) }
                }
            }
        }
    }
}
