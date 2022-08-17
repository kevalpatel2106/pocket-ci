package com.kevalpatel2106.feature.account.list

import com.kevalpatel2106.entity.id.AccountId

internal sealed class AccountListVMEvent {
    data class OpenProjects(val accountId: AccountId) : AccountListVMEvent()
    data class ShowDeleteConfirmation(val accountId: AccountId, val name: String) :
        AccountListVMEvent()

    object AccountRemovedSuccess : AccountListVMEvent()
    object OpenCiSelection : AccountListVMEvent()
    object RefreshAccountList : AccountListVMEvent()
    object Close : AccountListVMEvent()
    object RetryLoading : AccountListVMEvent()
    object ShowErrorSelectingAccount : AccountListVMEvent()
    object ShowErrorRemovingAccount : AccountListVMEvent()
}
