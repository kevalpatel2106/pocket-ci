package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.feature.account.R
import com.kevalpatel2106.feature.account.list.AccountsListItem.AccountItem
import com.kevalpatel2106.repository.CIInfoRepo
import javax.inject.Inject

class AccountItemMapperImpl @Inject constructor(
    private val ciInfoRepo: CIInfoRepo,
) : AccountItemMapper {

    override suspend fun invoke(account: Account): AccountItem {
        val ciRepo = ciInfoRepo.getCIInfo(account.type)
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
