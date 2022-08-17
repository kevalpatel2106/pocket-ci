package com.kevalpatel2106.feature.account.list.adapter

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId

internal interface AccountListAdapterCallback {
    fun onAccountSelected(accountId: AccountId)
    fun onAccountRemoved(account: Account)
}
