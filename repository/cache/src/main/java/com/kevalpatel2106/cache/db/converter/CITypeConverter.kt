package com.kevalpatel2106.cache.db.converter

import androidx.room.TypeConverter
import com.kevalpatel2106.entity.CIType

internal object CITypeConverter {
    @JvmStatic
    @TypeConverter
    fun toCiType(value: Int): CIType = CIType.values().first { it.id == value }

    @JvmStatic
    @TypeConverter
    fun fromCIType(value: CIType): Int = value.id
}
