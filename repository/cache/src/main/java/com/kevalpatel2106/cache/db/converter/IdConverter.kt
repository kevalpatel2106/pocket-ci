package com.kevalpatel2106.cache.db.converter

import androidx.room.TypeConverter
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toAccountIdOrNull
import com.kevalpatel2106.entity.id.toProjectIdOrNull

internal object IdConverter {

    @JvmStatic
    @TypeConverter
    fun toAccountId(value: Long?): AccountId? = value.toAccountIdOrNull()

    @JvmStatic
    @TypeConverter
    fun fromAccountId(value: AccountId?): Long? = value?.getValue()

    @JvmStatic
    @TypeConverter
    fun toProjectId(value: String?): ProjectId? = value.toProjectIdOrNull()

    @JvmStatic
    @TypeConverter
    fun fromProjectId(value: ProjectId?): String? = value?.getValue()
}
