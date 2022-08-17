package com.kevalpatel2106.repositoryImpl.cache.db.converter

import androidx.room.TypeConverter
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.toUrlOrNull

internal object UrlConverter {

    @JvmStatic
    @TypeConverter
    fun toUrl(value: String?): Url? = value.toUrlOrNull()

    @JvmStatic
    @TypeConverter
    fun fromUrl(value: Url?): String? = value?.value
}
