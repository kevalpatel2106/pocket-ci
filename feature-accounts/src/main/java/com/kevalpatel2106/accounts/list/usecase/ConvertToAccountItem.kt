package com.kevalpatel2106.accounts.list.usecase

import com.kevalpatel2106.accounts.list.adapter.AccountsListItem
import com.kevalpatel2106.entity.Account

interface ConvertToAccountItem {
    suspend operator fun invoke(account: Account): AccountsListItem.AccountItem
}
