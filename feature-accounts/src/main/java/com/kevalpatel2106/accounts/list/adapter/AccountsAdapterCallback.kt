package com.kevalpatel2106.accounts.list.adapter

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId

internal interface AccountsAdapterCallback {
    fun onAccountSelected(accountId: AccountId)
    fun onAccountRemoved(account: Account)
}
