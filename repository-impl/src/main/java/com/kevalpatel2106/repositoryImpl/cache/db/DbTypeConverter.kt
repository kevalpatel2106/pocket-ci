package com.kevalpatel2106.repositoryImpl.cache.db

import androidx.room.TypeConverter
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.toUrlOrNull
import java.util.Date

internal object DbTypeConverter {
    @JvmStatic
    @TypeConverter
    fun toUrl(value: String?): Url? = value.toUrlOrNull()

    @JvmStatic
    @TypeConverter
    fun fromUrl(value: Url?): String? = value?.value

    @JvmStatic
    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @JvmStatic
    @TypeConverter
    fun fromDate(value: Date): Long = value.time

    @JvmStatic
    @TypeConverter
    fun toCiType(value: Int): CIType = CIType.values().first { it.id == value }

    @JvmStatic
    @TypeConverter
    fun fromCIType(value: CIType): Int = value.id
}
