package com.kevalpatel2106.repositoryImpl.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto
import com.kevalpatel2106.repositoryImpl.cache.db.converter.CITypeConverter
import com.kevalpatel2106.repositoryImpl.cache.db.converter.DateConverter
import com.kevalpatel2106.repositoryImpl.cache.db.converter.IdConverter
import com.kevalpatel2106.repositoryImpl.cache.db.converter.TokenConverter
import com.kevalpatel2106.repositoryImpl.cache.db.converter.UrlConverter
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
@TypeConverters(
    value = [
        DateConverter::class,
        UrlConverter::class,
        TokenConverter::class,
        CITypeConverter::class,
        IdConverter::class,
    ],
)
internal abstract class AppDb : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

    abstract fun getProjectDao(): ProjectDao

    companion object {
        private const val DB_NAME = "pocket_ci.db"

        internal fun create(
            application: Context,
            inMemory: Boolean,
        ) = if (inMemory) {
            Room.inMemoryDatabaseBuilder(application, AppDb::class.java)
        } else {
            Room.databaseBuilder(application, AppDb::class.java, DB_NAME)
        }.build()
    }
}
