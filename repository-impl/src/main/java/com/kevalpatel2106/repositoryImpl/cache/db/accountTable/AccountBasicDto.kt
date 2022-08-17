package com.kevalpatel2106.repositoryImpl.cache.db.accountTable

import androidx.room.ColumnInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId

internal data class AccountBasicDto(
    @ColumnInfo(name = AccountTableInfo.ID)
    var id: AccountId,

    @ColumnInfo(name = AccountTableInfo.TYPE)
    val type: CIType,

    @ColumnInfo(name = AccountTableInfo.BASE_URL)
    val baseUrl: Url,

    @ColumnInfo(name = AccountTableInfo.AUTH_TOKEN)
    val token: Token,
)
