package com.kevalpatel2106.cache.db.projectLocalDataTable

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.getProjectLocalDataFixture
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
internal class ProjectLocalDataDaoTest {
    private val fixture = KFixture()
    private val projectLocalData1 = getProjectLocalDataFixture(fixture)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var subject: ProjectLocalDataDao

    @Before
    fun prepareDb() {
        hiltRule.inject()
    }

    @Test
    fun whenNoProjectLocalDataStored_testNoDataReturned() = runBlocking {
        val actual = subject.getAll()

        assertTrue(actual.isEmpty())
    }

    @Test
    fun whenNewProjectLocalDataStored_verifyProjectDataInserted() = runBlocking {
        subject.updateLocalData(projectLocalData1)

        assertEquals(listOf(projectLocalData1), subject.getAll())
    }

    @Test
    fun whenProjectLocalData_testTotalCount1() = runBlocking {
        val storedValue = projectLocalData1.copy(isPinned = true)
        subject.updateLocalData(storedValue)

        val updatedData = storedValue.copy(isPinned = false)
        subject.updateLocalData(updatedData)

        assertEquals(listOf(updatedData), subject.getAll())
    }
}
