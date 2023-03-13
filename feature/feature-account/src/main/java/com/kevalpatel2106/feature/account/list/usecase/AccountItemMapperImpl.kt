package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.feature.account.list.model.AccountsListItem.AccountItem
import com.kevalpatel2106.repository.CIInfoRepo
import javax.inject.Inject

class AccountItemMapperImpl @Inject constructor(
    private val ciInfoRepo: CIInfoRepo,
) : AccountItemMapper {

    override suspend fun invoke(account: Account): AccountItem {
        val ciRepo = ciInfoRepo.getCIInfo(account.type)
        return AccountItem(
            account = account,
            ciIcon = ciRepo.icon,
            ciName = ciRepo.name,
        )
    }
}
