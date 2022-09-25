package com.kevalpatel2106.cache.db.converter

import androidx.room.TypeConverter
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.toTokenOrNull

internal object TokenConverter {

    @JvmStatic
    @TypeConverter
    fun toToken(value: String?): Token? = value.toTokenOrNull()

    @JvmStatic
    @TypeConverter
    fun fromToken(value: Token?): String? = value?.getValue()
}
