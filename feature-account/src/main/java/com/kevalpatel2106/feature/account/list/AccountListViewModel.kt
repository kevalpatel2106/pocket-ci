package com.kevalpatel2106.feature.account.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RefreshAccountList
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RetryLoading
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorLoadingAccounts
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.adapter.AccountListAdapterCallback
import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapper
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.repository.AccountRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class AccountListViewModel @Inject constructor(
    private val accountRepo: AccountRepo,
    insertAccountListHeaders: InsertAccountListHeaders,
    accountItemMapper: AccountItemMapper,
    private val displayErrorMapper: DisplayErrorMapper,
) : BaseViewModel<AccountListVMEvent>(), AccountListAdapterCallback, NetworkStateCallback {

    val pageViewState = accountRepo.getAccounts()
        .mapNotNull { pagingData -> pagingData.map { account -> accountItemMapper(account) } }
        .mapNotNull { pagingData ->
            pagingData.insertSeparators { before, after -> insertAccountListHeaders(before, after) }
        }
        .catch { _vmEventsFlow.emit(ShowErrorLoadingAccounts(displayErrorMapper(it))) }
        .cachedIn(viewModelScope)

    override fun onAccountSelected(accountId: AccountId) {
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

    override fun onAccountRemoved(account: Account) {
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

    fun reload() = viewModelScope.launch { _vmEventsFlow.emit(RefreshAccountList) }

    override fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    fun onAddAccountClicked() = viewModelScope.launch { _vmEventsFlow.emit(OpenCiSelection) }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }
}
