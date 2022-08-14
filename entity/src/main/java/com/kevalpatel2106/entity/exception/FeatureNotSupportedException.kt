package com.kevalpatel2106.entity.exception

import com.kevalpatel2106.entity.CIType

data class FeatureNotSupportedException(
    val ciType: CIType,
    val msg: String,
) : IllegalStateException("${ciType.name}: $msg")
