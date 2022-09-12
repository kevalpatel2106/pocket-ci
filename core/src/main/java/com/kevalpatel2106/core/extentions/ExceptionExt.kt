package com.kevalpatel2106.core.extentions

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import retrofit2.HttpException
import java.net.HttpURLConnection

fun notSupported(ciType: CIType, msg: String): Nothing {
    throw FeatureNotSupportedException(ciType, msg)
}

fun Throwable.isUnAuthorized() =
    (this as? HttpException)?.code() == HttpURLConnection.HTTP_UNAUTHORIZED
