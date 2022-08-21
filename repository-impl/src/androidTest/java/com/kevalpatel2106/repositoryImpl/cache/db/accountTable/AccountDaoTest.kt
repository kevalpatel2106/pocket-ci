package com.kevalpatel2106.repositoryImpl.cache.db.accountTable

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.id.AccountId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
internal class AccountDaoTest {
    private val fixture = KFixture()
    private val accountDto1 = getAccountDtoFixture(fixture).copy(id = AccountId(1))
    private val accountDto2 = getAccountDtoFixture(fixture).copy(id = AccountId(2))
    private val testRecordSize = 2

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var subject: AccountDao

    @Before
    fun prepareDb() {
        hiltRule.inject()
    }

    private fun fillData() {
        runBlocking {
            subject.addUpdateAccount(accountDto1)
            subject.addUpdateAccount(accountDto2)
        }
    }

    @Test
    fun whenOneAccountWithGivenID_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(accountDto1.id.getValue())

        assertEquals(1, actual)
    }

    @Test
    fun whenNoAccountWithGivenID_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(-1L)

        assertEquals(0, actual)
    }

    @Test
    fun whenOneAccountGivenURLAndToken_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(accountDto1.baseUrl.value, accountDto1.token.getValue())

        assertEquals(1, actual)
    }

    @Test
    fun whenNoAccountGivenURLAndToken_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(accountDto2.baseUrl.value, accountDto1.token.getValue())

        assertEquals(0, actual)
    }

    @Test
    fun whenTwoAccountInDb_testTotalAccounts() = runBlocking {
        fillData()

        val actual = subject.getTotalAccounts()

        assertEquals(testRecordSize, actual)
    }

    @Test
    fun whenNoAccountInDb_testTotalAccounts() = runBlocking {
        val actual = subject.getTotalAccounts()

        assertEquals(0, actual)
    }

    @Test
    fun whenAccountWithGivenIDStored_testGetAccount() = runBlocking {
        fillData()

        val actual = subject.getAccount(accountDto1.id.getValue())

        assertEquals(accountDto1, actual)
    }

    @Test
    fun whenAccountWithGivenIDStored_testGetAccountBasic() = runBlocking {
        fillData()

        val actual = subject.getAccountBasic(accountDto1.id.getValue())

        val expected = AccountBasicDto(
            id = accountDto1.id,
            token = accountDto1.token,
            baseUrl = accountDto1.baseUrl,
            type = accountDto1.type,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun whenTwoAccountsStored_testGetFirstAccount() = runBlocking {
        fillData()

        val actual = subject.getFirstAccount()

        assertEquals(accountDto1, actual)
    }

    @Test
    fun whenNoAccountsStored_testGetFirstAccount() = runBlocking {
        val actual = subject.getFirstAccount()

        assertNull(actual)
    }

    @Test
    fun whenAccountWithGivenIDAlreadyStored_testAddUpdateAccount() = runBlocking {
        fillData()

        val actual = subject.addUpdateAccount(accountDto1)

        assertEquals(accountDto1.id.getValue(), actual)
        assertEquals(testRecordSize, subject.getTotalAccounts())
    }

    @Test
    fun whenAccountWithNewIDAlreadyStored_testAddUpdateAccount() = runBlocking {
        fillData()

        subject.addUpdateAccount(accountDto1.copy(id = AccountId.EMPTY, token = fixture()))

        assertEquals(testRecordSize + 1, subject.getTotalAccounts())
    }

    @Test
    fun whenEmailAndTokenCombinationAlreadyPresentForAccountToAdd_testAddUpdateAccount() =
        runBlocking {
            fillData()

            subject.addUpdateAccount(accountDto1.copy(id = AccountId.EMPTY))

            assertEquals(testRecordSize, subject.getTotalAccounts())
        }

    @Test
    fun whenNextPageCursorProvided_testUpdateNextPageCursor() = runBlocking {
        fillData()
        val cursor = fixture<String>()

        subject.updateNextPageCursor(accountDto1.id.getValue(), cursor)

        assertEquals(cursor, subject.getAccount(accountDto1.id.getValue()).nextProjectCursor)
    }

    @Test
    fun whenNextPageCursorNull_testUpdateNextPageCursor() = runBlocking {
        fillData()
        val cursor = null

        subject.updateNextPageCursor(accountDto1.id.getValue(), cursor)

        assertNull(subject.getAccount(accountDto1.id.getValue()).nextProjectCursor)
    }

    @Test
    fun whenAccountWithGivenIdStored_testGetLastProjectRefreshEpoch() = runBlocking {
        fillData()

        val lastRefreshEpoch = subject.getLastProjectRefreshEpoch(accountDto1.id.getValue())

        assertEquals(accountDto1.projectCacheLastUpdated, lastRefreshEpoch)
    }

    @Test
    fun whenLastProjectRefreshEpochGiven_testUpdateLastProjectRefreshEpoch() = runBlocking {
        fillData()
        val lastUpdateEpoch = System.currentTimeMillis()

        subject.updateLastProjectRefreshEpoch(accountDto1.id.getValue(), lastUpdateEpoch)

        assertEquals(
            lastUpdateEpoch,
            subject.getAccount(accountDto1.id.getValue()).projectCacheLastUpdated,
        )
    }

    @Test
    fun whenAccountToDeleteStored_testDeleteAccount() = runBlocking {
        fillData()

        subject.deleteAccount(accountDto1.id.getValue())

        assertEquals(1, subject.getTotalAccounts())
    }

    private fun getAccountDtoFixture(fixture: KFixture) = AccountDto(
        id = getAccountIdFixture(fixture),
        type = fixture(),
        baseUrl = getUrlFixture(fixture),
        token = fixture(),
        avatar = getUrlFixture(fixture),
        email = fixture(),
        name = fixture(),
        nextProjectCursor = fixture(),
        savedAt = fixture(),
        projectCacheLastUpdated = fixture(),
    )
}
