package com.kevalpatel2106.core.errorHandling

import android.app.Application
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.R
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import com.kevalpatel2106.entity.exception.NoInternetException
import com.squareup.moshi.JsonDataException
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import retrofit2.HttpException
import retrofit2.Response
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.util.Date

internal class DisplayErrorMapperImplTest {
    private val application = mock<Application> {
        resourceMap.forEach { (res, value) ->
            on { getString(res) } doReturn value
            on { getString(eq(res), anyString(), anyString()) } doReturn value
        }
    }
    private val httpErrorMessageMapper = mock<HttpErrorMessageMapper> {
        on { invoke(any()) } doReturn (testHttpTitleRes to testHttpMessageRes)
    }
    private val subject = DisplayErrorMapperImpl(application, httpErrorMessageMapper)

    @ParameterizedTest(name = "given is error {0} when mapped then check display error")
    @MethodSource("provideValues")
    fun `given is error when mapped then check headline`(
        error: Throwable,
        shortMessage: Boolean,
        expectedDisplayError: DisplayError,
    ) {
        assertEquals(expectedDisplayError, subject(error, shortMessage, now))
    }

    companion object {
        private val fixture = KFixture()
        private val now = Date()
        private val testHttpTitleRes = fixture<Int>()
        private val testHttpMessageRes = fixture<Int>()
        private val httpErrorBody = fixture<String>()

        private val noInternetExc = NoInternetException(fixture())
        private val socketTimeOutExc = SocketTimeoutException(fixture())
        private val noRouteToHostExc = NoRouteToHostException(fixture())
        private val featureNotSupportedExc = FeatureNotSupportedException(fixture(), fixture())
        private val httpExc = HttpException(
            Response.error<String>(400, ResponseBody.create(fixture(), httpErrorBody)),
        )
        private val jsonDataExc = JsonDataException()
        private val illegalStateExc = IllegalStateException()
        private val throwable = Throwable()

        private val resourceMap = mapOf<Int, String>(
            R.string.error_unknown_message_short to fixture(),
            R.string.error_unknown_message to fixture(),
            R.string.error_unknown_title to fixture(),

            R.string.error_json_parsing_title to fixture(),
            R.string.error_json_parsing_message to fixture(),

            R.string.error_feature_not_supported_title to fixture(),
            R.string.error_feature_not_supported_message to fixture(),

            R.string.error_no_internet_title to fixture(),
            R.string.error_no_internet_message to fixture(),

            testHttpTitleRes to fixture(),
            testHttpMessageRes to fixture(),
        )

        @Suppress("unused", "LongMethod")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: throwable, display error
            arguments(
                noInternetExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = noInternetExc,
                    headline = resourceMap.getValue(R.string.error_no_internet_title),
                    message = resourceMap.getValue(R.string.error_no_internet_message),
                    technicalMessage = noInternetExc.message,
                    url = noInternetExc.url,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = false,
                ),
            ),
            arguments(
                socketTimeOutExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = socketTimeOutExc,
                    headline = resourceMap.getValue(R.string.error_no_internet_title),
                    message = resourceMap.getValue(R.string.error_no_internet_message),
                    technicalMessage = socketTimeOutExc.message,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = false,
                ),
            ),
            arguments(
                noRouteToHostExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = noRouteToHostExc,
                    headline = resourceMap.getValue(R.string.error_no_internet_title),
                    message = resourceMap.getValue(R.string.error_no_internet_message),
                    technicalMessage = noRouteToHostExc.message,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = false,
                ),
            ),
            arguments(
                featureNotSupportedExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = featureNotSupportedExc,
                    headline = resourceMap.getValue(R.string.error_feature_not_supported_title),
                    message = resourceMap.getValue(R.string.error_feature_not_supported_message),
                    technicalMessage = featureNotSupportedExc.msg,
                    time = now,
                    nonRecoverable = true,
                    unableToTriage = false,
                ),
            ),
            arguments(
                httpExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = httpExc,
                    headline = resourceMap.getValue(testHttpTitleRes),
                    message = resourceMap.getValue(testHttpMessageRes),
                    technicalMessage = httpExc.message,
                    httpResponseCode = httpExc.code(),
                    url = httpExc.response()?.raw()?.request?.url.toString(),
                    httpResponse = httpErrorBody,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = false,
                ),
            ),
            arguments(
                jsonDataExc,
                fixture<Boolean>(),
                DisplayError(
                    throwable = jsonDataExc,
                    headline = resourceMap.getValue(R.string.error_json_parsing_title),
                    message = resourceMap.getValue(R.string.error_json_parsing_message),
                    technicalMessage = jsonDataExc.message,
                    time = now,
                    nonRecoverable = true,
                    unableToTriage = false,
                ),
            ),
            arguments(
                illegalStateExc,
                false,
                DisplayError(
                    throwable = illegalStateExc,
                    headline = resourceMap.getValue(R.string.error_unknown_title),
                    message = resourceMap.getValue(R.string.error_unknown_message),
                    technicalMessage = illegalStateExc.message,
                    time = now,
                    nonRecoverable = true,
                    unableToTriage = true,
                ),
            ),
            arguments(
                throwable,
                false,
                DisplayError(
                    throwable = throwable,
                    headline = resourceMap.getValue(R.string.error_unknown_title),
                    message = resourceMap.getValue(R.string.error_unknown_message),
                    technicalMessage = throwable.message,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = true,
                ),
            ),
            arguments(
                throwable,
                true,
                DisplayError(
                    throwable = throwable,
                    headline = resourceMap.getValue(R.string.error_unknown_title),
                    message = resourceMap.getValue(R.string.error_unknown_message_short),
                    technicalMessage = throwable.message,
                    time = now,
                    nonRecoverable = false,
                    unableToTriage = true,
                ),
            ),
        )
    }
}
