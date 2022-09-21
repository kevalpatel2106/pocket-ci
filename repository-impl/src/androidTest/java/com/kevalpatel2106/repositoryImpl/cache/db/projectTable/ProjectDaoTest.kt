package com.kevalpatel2106.repositoryImpl.cache.db.projectTable

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.getAccountDtoFixture
import com.kevalpatel2106.repositoryImpl.getProjectDtoFixture
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
internal class ProjectDaoTest {
    private val fixture = KFixture()
    private val accountDto1 = getAccountDtoFixture(fixture)
    private val accountDto2 = getAccountDtoFixture(fixture)
    private val projectDto1 = getProjectDtoFixture(fixture).copy(accountId = accountDto1.id)
    private val projectDto2 = getProjectDtoFixture(fixture).copy(accountId = accountDto2.id)
    private val projectDtos = listOf(projectDto1, projectDto2)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var accountDao: AccountDao

    @Inject
    lateinit var subject: ProjectDao

    @Before
    fun prepareDb() {
        hiltRule.inject()
    }

    private fun fillData() {
        runBlocking {
            accountDao.addUpdateAccount(accountDto1)
            accountDao.addUpdateAccount(accountDto2)
            subject.addUpdateProjects(projectDtos)
        }
    }

    @Test
    fun whenProjectsStored_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(
            projectDto1.remoteId.getValue(),
            projectDto1.accountId.getValue(),
        )

        assertEquals(1, actual)
    }

    @Test
    fun whenProjectsNotStoredForRemoteIDAndAccountID_testGetCount() = runBlocking {
        fillData()

        val actual = subject.getCount(fixture(), fixture())

        assertEquals(0, actual)
    }

    @Test
    fun whenProjectsStored_testGetProject() = runBlocking {
        fillData()

        val actual = subject.getProject(
            projectDto1.remoteId.getValue(),
            projectDto1.accountId.getValue(),
        )

        assertEquals(projectDto1.remoteId, actual.project.remoteId)
    }

    @Test
    fun whenProjectsStored_testGetProjectBasic() = runBlocking {
        fillData()

        val actual = subject.getProjectBasic(
            projectDto1.remoteId.getValue(),
            projectDto1.accountId.getValue(),
        )

        val expected = ProjectBasicDto(
            remoteId = projectDto1.remoteId,
            name = projectDto1.name,
            owner = projectDto1.owner,
            accountId = projectDto1.accountId,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun whenProjectToAddAlreadyStored_testAddUpdateProjects() = runBlocking {
        fillData()

        subject.addUpdateProjects(listOf(projectDto1))

        assertEquals(projectDtos.size, subject.getTotalProjects())
    }

    @Test
    fun whenProjectToAddNotStored_testAddUpdateProjects() = runBlocking {
        fillData()

        subject.addUpdateProjects(
            listOf(projectDto1.copy(remoteId = fixture(), accountId = accountDto1.id)),
        )

        assertEquals(projectDtos.size + 1, subject.getTotalProjects())
    }

    @Test
    fun whenProjectWithAccountIdToDeleteStored_testDeleteProjects() = runBlocking {
        fillData()
        val accountId = projectDto1.accountId

        subject.deleteProjects(accountId.getValue())

        assertEquals(projectDtos.size - 1, subject.getTotalProjects())
    }

    @Test
    fun whenProjectWithAccountIdToDeleteNotStored_testDeleteProjects() = runBlocking {
        fillData()
        val accountId = fixture<AccountId>()

        subject.deleteProjects(accountId.getValue())

        assertEquals(projectDtos.size, subject.getTotalProjects())
    }

    @Test
    fun givenOldProjectsInTheTable_whenReplacingWithNewProjects_testProjectsForTheAccountDeletedAndReplaced() =
        runBlocking {
            fillData()
            val newProjectDtos = listOf(
                getProjectDtoFixture(fixture).copy(accountId = accountDto1.id),
                getProjectDtoFixture(fixture).copy(accountId = accountDto1.id),
            )

            subject.replaceWithNewProjects(accountDto1.id.getValue(), newProjectDtos)

            val expectedTableSize =
                newProjectDtos.size + projectDtos.filter { it.accountId == accountDto2.id }.size
            assertEquals(expectedTableSize, subject.getTotalProjects())
            newProjectDtos.forEach {
                assertEquals(
                    1,
                    subject.getCount(it.remoteId.getValue(), accountDto1.id.getValue())
                )
            }
            assertEquals(
                1,
                subject.getCount(projectDto2.remoteId.getValue(), accountDto2.id.getValue())
            )
        }
}
