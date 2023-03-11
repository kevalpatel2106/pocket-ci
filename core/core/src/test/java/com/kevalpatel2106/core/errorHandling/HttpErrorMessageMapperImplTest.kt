package com.kevalpatel2106.core.errorHandling

import com.kevalpatel2106.core.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_ACCEPTABLE
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_PRECON_FAILED
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import java.net.HttpURLConnection.HTTP_UNSUPPORTED_TYPE

internal class  HttpErrorMessageMapperImplTest {
    private val subject = HttpErrorMessageMapperImpl()

    @ParameterizedTest(name = "given is http response code {0} when mapped then check title {1}")
    @MethodSource("provideValues")
    fun `given is http response code when mapped then check title`(
        code: Int,
        titleRes: Int,
        messageRes: Int,
    ) {
        assertEquals(titleRes, subject(code).first)
    }

    @ParameterizedTest(name = "given is http response code {0} when mapped then check message {2}")
    @MethodSource("provideValues")
    fun `given is http response code when mapped then check message`(
        code: Int,
        titleRes: Int,
        messageRes: Int,
    ) {
        assertEquals(messageRes, subject(code).second)
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = mutableListOf(
            arguments(
                HTTP_UNAUTHORIZED,
                R.string.error_unauthorized_title,
                R.string.error_unauthorized_message,
            ),
            arguments(
                HTTP_FORBIDDEN,
                R.string.error_unauthorized_title,
                R.string.error_unauthorized_message,
            ),
            arguments(
                300, // other response code
                R.string.error_unknown_title,
                R.string.error_unknown_message,
            ),
        ).apply {
            (HTTP_NOT_FOUND..HTTP_NOT_ACCEPTABLE).forEach { code ->
                add(
                    arguments(
                        code,
                        R.string.error_bad_request_title,
                        R.string.error_bad_request_message,
                    ),
                )
            }
            (HTTP_PRECON_FAILED..HTTP_UNSUPPORTED_TYPE).forEach { code ->
                add(
                    arguments(
                        code,
                        R.string.error_bad_request_title,
                        R.string.error_bad_request_message,
                    ),
                )
            }
            (HTTP_INTERNAL_ERROR..HTTP_UNAVAILABLE).forEach { code ->
                add(
                    arguments(
                        code,
                        R.string.error_server_down_title,
                        R.string.error_server_down_message,
                    ),
                )
            }
        }
    }
}
