package com.kevalpatel2106.repositoryImpl.cache.db

import android.app.Application
import androidx.room.AutoMigration
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
import com.kevalpatel2106.repositoryImpl.cache.db.crypto.SqlcipherFactory
import com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable.ProjectLocalDataDto
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import com.kevalpatel2106.repositoryImpl.di.EnableEncryption
import javax.inject.Inject

@Database(
    entities = [
        AccountDto::class,
        ProjectDto::class,
        ProjectLocalDataDto::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
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

    class Factory @Inject constructor(
        private val application: Application,
        @EnableEncryption private val encryptionEnabled: Boolean,
        private val sqlcipherFactory: SqlcipherFactory,
    ) {
        fun create(inMemory: Boolean) = if (inMemory) {
            Room.inMemoryDatabaseBuilder(application, AppDb::class.java)
        } else {
            Room.databaseBuilder(application, AppDb::class.java, DB_NAME)
        }.apply {
            if (encryptionEnabled) openHelperFactory(sqlcipherFactory.create())
        }.build()
    }

    companion object {
        private const val DB_NAME = "pocket_ci.db"
    }
}
