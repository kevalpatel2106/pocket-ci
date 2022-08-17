package com.kevalpatel2106.repositoryImpl.cache.db.converter

import androidx.room.TypeConverter
import java.util.Date

internal object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @JvmStatic
    @TypeConverter
    fun fromDate(value: Date): Long = value.time
}
