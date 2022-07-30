package com.kevalpatel2106.accounts.list.usecase

import com.kevalpatel2106.accounts.R
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem.AccountItem
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.repository.CIInfoRepo
import javax.inject.Inject

class ConvertToAccountItemImpl @Inject constructor(
    private val ciInfoRepo: CIInfoRepo,
) : ConvertToAccountItem {

    override suspend fun invoke(account: Account): AccountItem {
        val ciRepo = ciInfoRepo.getCI(account.type)
        val strokeWidth = if (account.isSelected) {
            R.dimen.selected_account_stroke_width
        } else {
            R.dimen.unselected_account_stroke_width
        }
        return AccountItem(
            account = account,
            ciIcon = ciRepo.icon,
            ciName = ciRepo.name,
            imageStrokeWidth = strokeWidth,
        )
    }
}
