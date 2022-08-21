package com.kevalpatel2106.repositoryImpl.account

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountDtoMapper
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountMapper
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStore
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AccountRepoImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val projectDao: ProjectDao,
    private val appDataStore: AppDataStore,
    private val accountMapper: AccountMapper,
    private val accountDtoMapper: AccountDtoMapper,
    private val connectorFactory: CIConnectorFactory,
) : AccountRepo {

    override suspend fun hasAnyAccount() = accountDao.getTotalAccounts() > 0

    override suspend fun hasAccount(url: Url, token: Token): Boolean {
        return accountDao.getCount(url.value, token.getValue()) > 0
    }

    override suspend fun addAccount(ciType: CIType, url: Url, token: Token): Account {
        // fetch
        val account = connectorFactory.get(ciType).getMyAccountInfo(url, token)

        // cache
        val accountDto = accountDtoMapper(account.copy(localId = AccountId.EMPTY))
        val newId = accountDao.addUpdateAccount(accountDto).toAccountId()

        // retrieve
        setSelectedAccount(newId) // Automatically select new account.
        return getAccount(newId)
    }

    override suspend fun removeAccount(accountId: AccountId) {
        accountDao.deleteAccount(accountId.getValue())
        getSelectedAccount() // Reset the selected account if the deleted account was selected.
        projectDao.deleteProjects(accountId.getValue())
    }

    override suspend fun getAccount(accountId: AccountId): Account {
        val accountDto = accountDao.getAccount(accountId.getValue())
        val activeId = appDataStore.getSelectedAccountId()
        return accountMapper(accountDto, isSelected = activeId == accountDto.id)
    }

    override fun getAccounts(): Flow<PagingData<Account>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { accountDao.getAccounts() },
        ).flow.map { accountDtos ->
            val activeId = appDataStore.getSelectedAccountId()
            accountDtos.map { accountMapper(it, isSelected = activeId == it.id) }
        }
    }

    override suspend fun getSelectedAccount() = observeSelectedAccount().first()

    override fun observeSelectedAccount(): Flow<Account> =
        appDataStore.observeSelectedAccountId()
            .map { selectedAccountId ->
                if (selectedAccountId == null || !hasAccount(selectedAccountId)) {
                    // No account selected.
                    val firstAccountDto = accountDao.getFirstAccount()
                    requireNotNull(firstAccountDto) { "There are no accounts in the database." }

                    appDataStore.updateSelectedAccountId(firstAccountDto.id)
                    accountMapper(firstAccountDto, isSelected = true)
                } else {
                    val accountDto = accountDao.getAccount(selectedAccountId.getValue())
                    accountMapper(accountDto, isSelected = true)
                }
            }

    override suspend fun setSelectedAccount(accountId: AccountId) {
        appDataStore.updateSelectedAccountId(accountId)
    }

    private suspend fun hasAccount(accountId: AccountId) =
        accountDao.getCount(accountId.getValue()) > 0

    companion object {
        private const val PAGE_SIZE = 20
    }
}
