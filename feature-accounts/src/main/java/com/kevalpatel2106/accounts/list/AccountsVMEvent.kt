package com.kevalpatel2106.accounts.list

import com.kevalpatel2106.entity.id.AccountId

internal sealed class AccountsVMEvent {
    data class OpenProjects(val accountId: AccountId) : AccountsVMEvent()
    data class ShowDeleteConfirmation(val accountId: AccountId, val name: String) :
        AccountsVMEvent()

    object AccountRemovedSuccess : AccountsVMEvent()
    object OpenCiSelection : AccountsVMEvent()
    object RefreshAccounts : AccountsVMEvent()
    object RetryLoading : AccountsVMEvent()
    object ShowErrorSelectingAccount : AccountsVMEvent()
    object ShowErrorRemovingAccount : AccountsVMEvent()
    object ShowErrorView : AccountsVMEvent()
}
