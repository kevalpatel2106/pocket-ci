package com.kevalpatel2106.accounts.list.usecase

import com.kevalpatel2106.accounts.list.adapter.AccountsListItem
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem.AccountItem

interface InsertAccountListHeaders {
    operator fun invoke(before: AccountItem?, after: AccountItem?): AccountsListItem?
}
