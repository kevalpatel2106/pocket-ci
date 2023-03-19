package com.kevalpatel2106.core.errorHandling

import com.kevalpatel2106.core.resources.R
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_ACCEPTABLE
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_PRECON_FAILED
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import java.net.HttpURLConnection.HTTP_UNSUPPORTED_TYPE
import javax.inject.Inject

internal class HttpErrorMessageMapperImpl @Inject constructor() : HttpErrorMessageMapper {

    override operator fun invoke(code: Int): Pair<Int, Int> = when (code) {
        HTTP_UNAUTHORIZED, HTTP_FORBIDDEN -> {
            R.string.error_unauthorized_title to R.string.error_unauthorized_message
        }
        in HTTP_NOT_FOUND..HTTP_NOT_ACCEPTABLE, in HTTP_PRECON_FAILED..HTTP_UNSUPPORTED_TYPE -> {
            R.string.error_bad_request_title to R.string.error_bad_request_message
        }
        in HTTP_INTERNAL_ERROR..HTTP_UNAVAILABLE -> {
            R.string.error_server_down_title to R.string.error_server_down_message
        }
        else -> R.string.error_unknown_title to R.string.error_unknown_message
    }
}
