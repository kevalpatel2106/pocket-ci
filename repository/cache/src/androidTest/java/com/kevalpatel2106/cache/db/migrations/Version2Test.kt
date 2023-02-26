package com.kevalpatel2106.cache.db.migrations

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kevalpatel2106.cache.db.AppDb
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class Version2Test {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDb::class.java,
        listOf<AutoMigrationSpec>(),
        FrameworkSQLiteOpenHelperFactory(),
    )

    @Test
    fun migrate1To2() {
        // Prepare V1 DB
        val v1Accounts = testDataForV2AccountTable()
        val v1Projects = testDataForV1ProjectTable()
        helper.createDatabase(TEST_DB, 1).apply {
            // https://github.com/kevalpatel2106/pocket-ci/blob/6a0ba7989fb8de6b79aaff088f98e12849eb1b85
            insert("account", SQLiteDatabase.CONFLICT_REPLACE, v1Accounts)
            insert("projects", SQLiteDatabase.CONFLICT_REPLACE, v1Projects)
            close()
        }

        // Run migration
        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true)

        // Verify data in V2
        assertTrue(db.isDatabaseIntegrityOk)

        // Test account table
        val accountCursor = db.query("SELECT * FROM account")
        assertEquals(1, accountCursor.count)
        accountCursor.moveToNext()
        val longAccountKeys = listOf(
            "id",
            "type",
            "last_project_refresh",
            "_saved_at",
        )
        accountCursor.columnNames.forEach { name ->
            val index = accountCursor.getColumnIndex(name)
            val actual = if (longAccountKeys.contains(name)) {
                accountCursor.getLong(index).toString()
            } else {
                accountCursor.getString(index)
            }
            assertEquals(actual, v1Accounts.get(name).toString())
        }

        // Test project table
        val projectCursor = db.query("SELECT * FROM projects")
        assertEquals(1, projectCursor.count)
        projectCursor.moveToNext()
        val longProjectKeys = listOf(
            "account_id",
            "is_disabled",
            "is_public",
            "last_updated_at",
            "_saved_at",
        )
        projectCursor.columnNames.forEach { name ->
            val index = projectCursor.getColumnIndex(name)
            val actual = if (longProjectKeys.contains(name)) {
                projectCursor.getLong(index).toString()
            } else {
                projectCursor.getString(index)
            }
            assertEquals(actual, v1Projects.get(name).toString())
        }

        // Project local data table
        val projectLocalDataCursor = db.query("SELECT * FROM project_local_data")
        assertEquals(0, projectLocalDataCursor.count)
    }

    private fun testDataForV1ProjectTable() = ContentValues().apply {
        // Keys from the project table in v1
        put("remote_id", "1sdjkfbhads")
        put("name", "pocket-ci")
        put("owner", "kevalpatel2106")
        put("account_id", 1L)
        put("repo_url", "http://google.com")
        put("image_url", "http://image.com")
        put("is_disabled", 0)
        put("is_public", 0)
        put("last_updated_at", System.currentTimeMillis().toInt())
        put("_saved_at", System.currentTimeMillis().toInt())
    }

    private fun testDataForV2AccountTable() = ContentValues().apply {
        // Keys from the account table in v1
        put("id", 1L)
        put("name", "pocket-ci")
        put("email", "example@gmail.com")
        put("avatar", "http://image.com")
        put("type", 0)
        put("url", "http://google.com")
        put("token", "test-token")
        put("project_cursor", "next-project-cursor")
        put("last_project_refresh", System.currentTimeMillis())
        put("_saved_at", System.currentTimeMillis())
    }

    companion object {
        private const val TEST_DB = "test"
    }
}
