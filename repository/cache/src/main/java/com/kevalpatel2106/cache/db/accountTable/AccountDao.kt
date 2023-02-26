@file:SuppressWarnings("TooManyFunctions")

package com.kevalpatel2106.cache.db.accountTable

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountDao {
    @Query(
        "SELECT COUNT(${AccountTableInfo.ID}) FROM ${AccountTableInfo.TABLE_NAME} " +
            "WHERE ${AccountTableInfo.ID} = :accountId",
    )
    suspend fun getCount(accountId: Long): Int

    @Query(
        "SELECT COUNT(${AccountTableInfo.ID}) FROM ${AccountTableInfo.TABLE_NAME} " +
            "WHERE ${AccountTableInfo.BASE_URL} = :url AND ${AccountTableInfo.AUTH_TOKEN} = :token",
    )
    suspend fun getCount(url: String, token: String): Int

    @Query("SELECT COUNT(${AccountTableInfo.ID}) FROM ${AccountTableInfo.TABLE_NAME}")
    suspend fun getTotalAccounts(): Int

    @Query("SELECT * FROM ${AccountTableInfo.TABLE_NAME} WHERE ${AccountTableInfo.ID} = :accountId")
    suspend fun getAccount(accountId: Long): AccountDto

    @Query("SELECT * FROM ${AccountTableInfo.TABLE_NAME} WHERE ${AccountTableInfo.ID} = :accountId")
    suspend fun getAccountBasic(accountId: Long): AccountBasicDto

    @Query("SELECT * FROM ${AccountTableInfo.TABLE_NAME} ORDER BY ${AccountTableInfo.TYPE} ASC")
    fun getAccounts(): PagingSource<Int, AccountDto>

    @Query(
        "SELECT * FROM ${AccountTableInfo.TABLE_NAME} " +
            "ORDER BY ${AccountTableInfo.ID} ASC LIMIT 1",
    )
    suspend fun getFirstAccount(): AccountDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateAccount(accountDto: AccountDto): Long

    @Query(
        "UPDATE ${AccountTableInfo.TABLE_NAME} " +
            "SET ${AccountTableInfo.NEXT_PAGE_CURSOR} = :cursor " +
            "WHERE ${AccountTableInfo.ID} = :accountId",
    )
    suspend fun updateNextPageCursor(accountId: Long, cursor: String?)

    @Query(
        "SELECT ${AccountTableInfo.LAST_PROJECT_REFRESH} FROM ${AccountTableInfo.TABLE_NAME} " +
            "WHERE ${AccountTableInfo.ID} = :accountId LIMIT 1",
    )
    suspend fun getLastProjectRefreshEpoch(accountId: Long): Long

    @Query(
        "UPDATE ${AccountTableInfo.TABLE_NAME} " +
            "SET ${AccountTableInfo.LAST_PROJECT_REFRESH} = :epoch " +
            "WHERE ${AccountTableInfo.ID} = :accountId",
    )
    suspend fun updateLastProjectRefreshEpoch(accountId: Long, epoch: Long)

    @Query("DELETE FROM ${AccountTableInfo.TABLE_NAME} WHERE ${AccountTableInfo.ID} = :accountId")
    suspend fun deleteAccount(accountId: Long)
}
