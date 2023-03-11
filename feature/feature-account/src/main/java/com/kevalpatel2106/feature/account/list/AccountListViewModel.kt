package com.kevalpatel2106.feature.account.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorLoadingAccounts
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.menu.AccountMenuCallback
import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapper
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.AnalyticsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class AccountListViewModel @Inject constructor(
    private val accountRepo: AccountRepo,
    insertAccountListHeaders: InsertAccountListHeaders,
    accountItemMapper: AccountItemMapper,
    private val displayErrorMapper: DisplayErrorMapper,
    private val analyticsRepo: AnalyticsRepo,
) : ViewModel(), AccountMenuCallback {

    val pageViewState = accountRepo.getAccounts()
        .mapNotNull { pagingData ->
            pagingData.map { account -> accountItemMapper(account) }
        }
        .mapNotNull { pagingData ->
            pagingData.insertSeparators { before, after -> insertAccountListHeaders(before, after) }
        }
        .catch { _vmEventsFlow.emit(ShowErrorLoadingAccounts(displayErrorMapper(it))) }
        .cachedIn(viewModelScope)

    private val _vmEventsFlow = MutableSharedFlow<AccountListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(AccountListViewState.initialState())
    val viewState = _viewState.asStateFlow()

    fun onAccountSelected(accountId: AccountId) {
        viewModelScope.launch {
            runCatching { accountRepo.setSelectedAccount(accountId = accountId) }
                .onFailure { error ->
                    Timber.e(error)
                    _vmEventsFlow.emit(ShowErrorSelectingAccount(displayErrorMapper(error)))
                }.onSuccess {
                    _vmEventsFlow.emit(OpenProjects(accountId))
                }
        }
    }

    fun onAccountRemoved(account: Account) {
        viewModelScope.launch {
            _vmEventsFlow.emit(ShowDeleteConfirmation(account.localId, account.name))
        }
    }

    fun onAccountDeleteConfirmed(accountId: AccountId) {
        viewModelScope.launch {
            runCatching { accountRepo.removeAccount(accountId = accountId) }
                .onFailure { error ->
                    Timber.e(error)
                    _vmEventsFlow.emit(ShowErrorRemovingAccount(displayErrorMapper(error)))
                }.onSuccess {
                    _vmEventsFlow.emit(AccountRemovedSuccess)
                }
        }
    }

    fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    fun onAddAccountClicked() {
        analyticsRepo.sendEvent(ClickEvent(ClickEvent.Action.ACCOUNTS_ADD_ACCOUNT_CLICKED))
        viewModelScope.launch { _vmEventsFlow.emit(OpenCiSelection) }
    }

    override fun editModeStatus(on: Boolean) {
        viewModelScope.launch { _vmEventsFlow.emit(InvalidateOptionsMenu) }
        _viewState.update { it.copy(isEditModeOn = on) }
    }

    override fun isInEditMode() = _viewState.value.isEditModeOn
}
