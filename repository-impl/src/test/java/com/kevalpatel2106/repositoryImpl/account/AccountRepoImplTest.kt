package com.kevalpatel2106.repositoryImpl.account

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountDtoMapper
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountMapper
import com.kevalpatel2106.cache.dataStore.AppDataStore
import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.getAccountDtoFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class AccountRepoImplTest {
    private val fixture = KFixture()
    private val accountDto = getAccountDtoFixture(fixture)
    private val account = getAccountFixture(fixture)
    private val accountDao = mock<AccountDao>()
    private val appDataStore = mock<AppDataStore>()
    private val accountMapper = mock<AccountMapper> {
        on { invoke(any(), any()) } doReturn account
    }
    private val accountDtoMapper = mock<AccountDtoMapper> {
        on { invoke(any(), any()) } doReturn accountDto
    }
    private val connector = mock<CIConnector>()
    private val connectorFactory = mock<CIConnectorFactory> {
        on { get(any()) } doReturn connector
    }

    private val subject = AccountRepoImpl(
        accountDao,
        appDataStore,
        accountMapper,
        accountDtoMapper,
        connectorFactory,
    )

    @BeforeEach
    fun before() = runTest {
        whenever(appDataStore.getSelectedAccountId()).thenReturn(AccountId.EMPTY)
    }

    // region hasAnyAccount
    @Test
    fun `given no account stored when checking has any accounts then check it returns false`() =
        runTest {
            whenever(accountDao.getTotalAccounts()).doReturn(0)

            val actual = subject.hasAnyAccount()

            assertFalse(actual)
        }

    @Test
    fun `given account stored when checking has any accounts then check it returns false`() =
        runTest {
            whenever(accountDao.getTotalAccounts()).doReturn(fixture.range(1..100))

            val actual = subject.hasAnyAccount()

            assertTrue(actual)
        }
    // endregion

    // region hasAccount
    @Test
    fun `given no account stored with url and token when has accounts check then returns false`() =
        runTest {
            val url = getUrlFixture(fixture)
            val token = fixture<Token>()
            whenever(accountDao.getCount(url.value, token.getValue())).doReturn(0)

            val actual = subject.hasAccount(url, token)

            assertFalse(actual)
        }

    @Test
    fun `given account stored with url and token when has accounts check then returns true`() =
        runTest {
            val url = getUrlFixture(fixture)
            val token = fixture<Token>()
            whenever(
                accountDao.getCount(
                    url.value,
                    token.getValue(),
                ),
            ).doReturn(fixture.range(1..10))

            val actual = subject.hasAccount(url, token)

            assertTrue(actual)
        }
    // endregion

    // region addAccount
    @Test
    fun `given account fetch from CI fails when add account then check error thrown`() =
        runTest {
            val url = getUrlFixture(fixture)
            val token = fixture<Token>()
            whenever(connector.getMyAccountInfo(url, token)).doThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.addAccount(fixture(), url, token)
            }
        }

    @Test
    fun `given saving account fails when add account then verify error thrown`() =
        runTest {
            val url = getUrlFixture(fixture)
            val token = fixture<Token>()
            val accountToAdd = account.copy(baseUrl = url, authToken = token)
            val accountDtoToAdd = accountDto.copy(
                baseUrl = url,
                token = token,
                id = AccountId.EMPTY,
            )
            whenever(connector.getMyAccountInfo(url, token)).doReturn(accountToAdd)
            whenever(accountDtoMapper(accountToAdd)).doReturn(accountDtoToAdd)
            whenever(accountDao.addUpdateAccount(any())).doThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.addAccount(fixture(), url, token)
            }
        }

    @Test
    fun `given account fetch from CI passes when add account then check new account returned`() =
        runTest {
            val url = getUrlFixture(fixture)
            val newAccountID = getAccountIdFixture(fixture)
            val token = fixture<Token>()
            val accountToAdd = account.copy(baseUrl = url, authToken = token)
            val accountDtoToAdd = accountDto.copy(
                baseUrl = url,
                token = token,
                id = AccountId.EMPTY,
            )
            whenever(connector.getMyAccountInfo(url, token)).doReturn(accountToAdd)
            whenever(accountDtoMapper(accountToAdd)).doReturn(accountDtoToAdd)
            whenever(accountDao.addUpdateAccount(any())).doReturn(newAccountID.getValue())
            whenever(accountDao.getAccount(newAccountID.getValue())).doReturn(accountDtoToAdd)
            whenever(accountMapper(any(), any())).doReturn(accountToAdd)

            val actual = subject.addAccount(fixture(), url, token)

            assertEquals(accountToAdd, actual)
        }

    @Test
    fun `given account fetch from CI success when add account then verify account stored in db with empty id`() =
        runTest {
            val url = getUrlFixture(fixture)
            val newAccountID = getAccountIdFixture(fixture)
            val token = fixture<Token>()
            val accountToAdd = account.copy(baseUrl = url, authToken = token)
            val accountDtoToAdd = accountDto.copy(
                baseUrl = url,
                token = token,
                id = AccountId.EMPTY,
            )
            whenever(connector.getMyAccountInfo(url, token)).doReturn(accountToAdd)
            whenever(accountDtoMapper(accountToAdd)).doReturn(accountDtoToAdd)
            whenever(accountDao.addUpdateAccount(any())).doReturn(newAccountID.getValue())
            whenever(accountDao.getAccount(newAccountID.getValue())).doReturn(accountDtoToAdd)
            whenever(accountMapper(any(), any())).doReturn(accountToAdd)

            subject.addAccount(fixture(), url, token)

            verify(accountDtoMapper).invoke(
                check { assertEquals(it.localId, AccountId.EMPTY) },
                anyOrNull(),
            )
        }

    @Test
    fun `given account stored successfully when add account then verify new account selected`() =
        runTest {
            val url = getUrlFixture(fixture)
            val newAccountID = getAccountIdFixture(fixture)
            val token = fixture<Token>()
            val accountToAdd = account.copy(baseUrl = url, authToken = token)
            val accountDtoToAdd = accountDto.copy(
                baseUrl = url,
                token = token,
                id = AccountId.EMPTY,
            )
            whenever(connector.getMyAccountInfo(url, token)).doReturn(accountToAdd)
            whenever(accountDtoMapper(accountToAdd)).doReturn(accountDtoToAdd)
            whenever(accountDao.addUpdateAccount(any())).doReturn(newAccountID.getValue())
            whenever(accountDao.getAccount(newAccountID.getValue())).doReturn(accountDtoToAdd)
            whenever(accountMapper(any(), any())).doReturn(accountToAdd)

            subject.addAccount(fixture(), url, token)

            verify(appDataStore).updateSelectedAccountId(newAccountID)
        }
    // endregion

    // region removeAccount
    @Test
    fun `given account removes from storage successfully when removeAccount then verify account removes from db`() =
        runTest {
            val idToRemove = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(AccountId(100))
            whenever(accountDao.getAccount(any())).doReturn(accountDto)
            whenever(accountDao.getCount(any())).doReturn(1)
            whenever(accountMapper(any(), any())).doReturn(account)

            subject.removeAccount(idToRemove)

            verify(accountDao).deleteAccount(idToRemove.getValue())
        }

    @Test
    fun `given account removes from storage fails when removeAccount then error thrown`() =
        runTest {
            whenever(accountDao.deleteAccount(any())).doThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.removeAccount(getAccountIdFixture(fixture))
            }
        }

    @Test
    fun `given account removal successful when removeAccount currently selected then verify first account gets selected`() =
        runTest {
            val idToRemove = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(null)
            whenever(accountDao.getAccount(any())).doReturn(accountDto)
            whenever(accountDao.getCount(any())).doReturn(0)
            whenever(accountDao.getFirstAccount()).doReturn(accountDto)
            whenever(accountMapper(any(), any())).doReturn(account)

            subject.removeAccount(idToRemove)

            verify(accountDao).getFirstAccount()
            verify(appDataStore).updateSelectedAccountId(accountDto.id)
        }

    // endregion

    // region getAccount
    @Test
    fun `given account is selected when get account called then verify returned account selected`() =
        runTest {
            whenever(accountDao.getAccount(account.localId.getValue())).doReturn(accountDto)
            whenever(appDataStore.getSelectedAccountId()).doReturn(accountDto.id)

            subject.getAccount(account.localId)

            verify(accountMapper).invoke(any(), isSelected = eq(true))
        }

    @Test
    fun `given account is not selected when get account called then verify returned account not selected`() =
        runTest {
            whenever(accountDao.getAccount(account.localId.getValue())).doReturn(accountDto)
            whenever(appDataStore.getSelectedAccountId()).doReturn(AccountId.EMPTY)

            subject.getAccount(account.localId)

            verify(accountMapper).invoke(any(), isSelected = eq(false))
        }

    @Test
    fun `given error reading account from db when get account called then exception thrown`() =
        runTest {
            whenever(accountDao.getAccount(account.localId.getValue()))
                .doThrow(IllegalStateException())

            assertThrows<IllegalStateException> { subject.getAccount(account.localId) }
        }

    @Test
    fun `given error reading selected account id when get account called then exception thrown`() =
        runTest {
            whenever(accountDao.getAccount(account.localId.getValue())).doReturn(accountDto)
            whenever(appDataStore.getSelectedAccountId()).doThrow(IllegalStateException())

            assertThrows<IllegalStateException> { subject.getAccount(account.localId) }
        }
    // endregion

    // region setSelectedAccount
    @Test
    fun `given account id when selected account id set then verify id stored to datastore`() =
        runTest {
            val id = getAccountIdFixture(fixture)

            subject.setSelectedAccount(id)

            verify(appDataStore).updateSelectedAccountId(id)
        }
    // endregion

    // region getSelectedAccount
    @Test
    fun `given no account id selected when selected account fetch then first account in db returned`() =
        runTest {
            val firstAccountDto = getAccountDtoFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(null)
            whenever(accountDao.getFirstAccount()).doReturn(firstAccountDto)
            whenever(accountMapper(any(), any()))
                .doReturn(account.copy(localId = firstAccountDto.id))

            val actual = subject.getSelectedAccount()

            verify(accountMapper).invoke(firstAccountDto, isSelected = true)
            assertEquals(firstAccountDto.id, actual.localId)
        }

    @Test
    fun `given no account id selected when selected account fetch then first account id stored as selected`() =
        runTest {
            val firstAccountDto = getAccountDtoFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(null)
            whenever(accountDao.getFirstAccount()).doReturn(firstAccountDto)
            whenever(accountMapper(any(), any()))
                .doReturn(account.copy(localId = firstAccountDto.id))

            subject.getSelectedAccount()

            verify(appDataStore).updateSelectedAccountId(firstAccountDto.id)
        }

    @Test
    fun `given selected account id not exist when selected account fetch then first account id stored as selected`() =
        runTest {
            val firstAccountDto = getAccountDtoFixture(fixture)
            val givenSelectedId = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(givenSelectedId)
            whenever(accountDao.getCount(givenSelectedId.getValue())).doReturn(0)
            whenever(accountDao.getFirstAccount()).doReturn(firstAccountDto)
            whenever(accountMapper(any(), any()))
                .doReturn(account.copy(localId = firstAccountDto.id))

            subject.getSelectedAccount()

            verify(appDataStore).updateSelectedAccountId(firstAccountDto.id)
        }

    @Test
    fun `given no account id selected and no accounts in db when selected account fetch then exception thrown`() =
        runTest {
            whenever(appDataStore.getSelectedAccountId()).doReturn(null)
            whenever(accountDao.getFirstAccount()).doReturn(null)

            assertThrows<IllegalArgumentException> {
                subject.getSelectedAccount()
            }
        }

    @Test
    fun `given selected account id not exist and no accounts in db  when selected account fetch then exception thrown`() =
        runTest {
            val givenSelectedId = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(givenSelectedId)
            whenever(accountDao.getCount(givenSelectedId.getValue())).doReturn(0)
            whenever(accountDao.getFirstAccount()).doReturn(null)

            assertThrows<IllegalArgumentException> {
                subject.getSelectedAccount()
            }
        }

    @Test
    fun `given selected account id exist when selected account fetch then verify selected account fetched from db`() =
        runTest {
            val givenSelectedId = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(givenSelectedId)
            whenever(accountDao.getCount(givenSelectedId.getValue())).doReturn(1)
            whenever(accountDao.getAccount(any())).doReturn(accountDto)

            subject.getSelectedAccount()

            verify(accountDao).getAccount(givenSelectedId.getValue())
        }

    @Test
    fun `given selected account id exist when selected account fetch then verify returned account mapped as selected true`() =
        runTest {
            val givenSelectedId = getAccountIdFixture(fixture)
            whenever(appDataStore.getSelectedAccountId()).doReturn(givenSelectedId)
            whenever(accountDao.getCount(givenSelectedId.getValue())).doReturn(1)
            whenever(accountDao.getAccount(givenSelectedId.getValue())).doReturn(accountDto)

            subject.getSelectedAccount()

            verify(accountMapper).invoke(any(), isSelected = eq(true))
        }
    // endregion setSelectedAccount
}
