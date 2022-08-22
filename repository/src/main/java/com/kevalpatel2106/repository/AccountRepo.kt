package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import kotlinx.coroutines.flow.Flow

interface AccountRepo {
    suspend fun hasAnyAccount(): Boolean
    suspend fun hasAccount(url: Url, token: Token): Boolean
    suspend fun addAccount(ciType: CIType, url: Url, token: Token): Account
    suspend fun removeAccount(accountId: AccountId)
    suspend fun getAccount(accountId: AccountId): Account

    fun getAccounts(): Flow<PagingData<Account>>

    suspend fun getSelectedAccount(): Account
    suspend fun setSelectedAccount(accountId: AccountId)
}
