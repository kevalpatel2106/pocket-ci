package com.kevalpatel2106.repositoryImpl.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto

@Database(
    entities = [
        AccountDto::class,
        ProjectDto::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DbTypeConverter::class)
internal abstract class AppDb : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

    abstract fun getProjectDao(): ProjectDao

    companion object {
        private const val DB_NAME = "pocket_ci.db"
        private lateinit var instance: AppDb

        internal fun create(
            application: Context,
            inMemory: Boolean,
        ): AppDb {
            instance = if (inMemory) {
                Room.inMemoryDatabaseBuilder(application, AppDb::class.java)
            } else {
                Room.databaseBuilder(application, AppDb::class.java, DB_NAME)
            }.build()
            return instance
        }
    }
}
