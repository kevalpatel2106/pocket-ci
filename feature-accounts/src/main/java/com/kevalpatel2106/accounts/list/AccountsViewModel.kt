package com.kevalpatel2106.accounts.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kevalpatel2106.accounts.list.AccountsVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.accounts.list.AccountsVMEvent.OpenCiSelection
import com.kevalpatel2106.accounts.list.AccountsVMEvent.OpenProjects
import com.kevalpatel2106.accounts.list.AccountsVMEvent.RefreshAccounts
import com.kevalpatel2106.accounts.list.AccountsVMEvent.RetryLoading
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorView
import com.kevalpatel2106.accounts.list.adapter.AccountsAdapterCallback
import com.kevalpatel2106.accounts.list.usecase.ConvertToAccountItem
import com.kevalpatel2106.accounts.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.repository.AccountRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class AccountsViewModel @Inject constructor(
    private val accountRepo: AccountRepo,
    insertAccountListHeaders: InsertAccountListHeaders,
    convertToAccountItem: ConvertToAccountItem,
) : BaseViewModel<AccountsVMEvent>(), AccountsAdapterCallback, NetworkStateCallback {

    val pageViewState = accountRepo.getAccounts()
        .mapNotNull { pagingData ->
            pagingData.map { account -> convertToAccountItem(account) }
        }
        .mapNotNull { pagingData ->
            pagingData.insertSeparators { before, after -> insertAccountListHeaders(before, after) }
        }
        .catch { _vmEventsFlow.emit(ShowErrorView) }
        .cachedIn(viewModelScope)

    override fun onAccountSelected(accountId: AccountId) {
        viewModelScope.launch {
            runCatching { accountRepo.setSelectedAccount(accountId = accountId) }
                .onFailure { error ->
                    Timber.e(error)
                    _vmEventsFlow.emit(ShowErrorSelectingAccount)
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
                    _vmEventsFlow.emit(ShowErrorRemovingAccount)
                }.onSuccess {
                    _vmEventsFlow.emit(AccountRemovedSuccess)
                }
        }
    }

    fun reload() {
        viewModelScope.launch { _vmEventsFlow.emit(RefreshAccounts) }
    }

    fun onAddAccountClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenCiSelection) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }
}
