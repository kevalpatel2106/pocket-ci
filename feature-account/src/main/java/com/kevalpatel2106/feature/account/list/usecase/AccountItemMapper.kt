package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.feature.account.list.AccountsListItem

interface AccountItemMapper {
    suspend operator fun invoke(account: Account): AccountsListItem.AccountItem
}
