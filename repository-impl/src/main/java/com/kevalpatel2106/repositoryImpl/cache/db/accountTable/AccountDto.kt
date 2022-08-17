package com.kevalpatel2106.repositoryImpl.cache.db.accountTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import java.util.Date

@Entity(
    tableName = AccountTableInfo.TABLE_NAME,
    indices = [
        Index(value = [AccountTableInfo.EMAIL, AccountTableInfo.AUTH_TOKEN], unique = true),
    ],
)
internal data class AccountDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AccountTableInfo.ID)
    var id: AccountId,

    @ColumnInfo(name = AccountTableInfo.NAME)
    val name: String,

    @ColumnInfo(name = AccountTableInfo.EMAIL)
    val email: String,

    @ColumnInfo(name = AccountTableInfo.AVATAR)
    val avatar: Url?,

    @ColumnInfo(name = AccountTableInfo.TYPE)
    val type: CIType,

    @ColumnInfo(name = AccountTableInfo.BASE_URL)
    val baseUrl: Url,

    @ColumnInfo(name = AccountTableInfo.AUTH_TOKEN)
    val token: Token,

    @ColumnInfo(name = AccountTableInfo.NEXT_PAGE_CURSOR)
    val nextProjectCursor: String?,

    @ColumnInfo(name = AccountTableInfo.LAST_PROJECT_REFRESH)
    val projectCacheLastUpdated: Long,

    @ColumnInfo(name = AccountTableInfo.SAVED_AT)
    val savedAt: Date,
)
