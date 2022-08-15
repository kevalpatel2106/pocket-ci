package com.kevalpatel2106.core.extentions

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException

fun notSupported(ciType: CIType, msg: String): Nothing {
    throw FeatureNotSupportedException(ciType, msg)
}
