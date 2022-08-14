package com.kevalpatel2106.core.errorHandling

import android.app.Application
import com.kevalpatel2106.core.R
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import com.kevalpatel2106.entity.exception.NoInternetException
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import javax.inject.Inject

internal class DisplayErrorMapperImpl @Inject constructor(
    private val application: Application,
    private val httpErrorMessageMapper: HttpErrorMessageMapper,
) : DisplayErrorMapper {

    override operator fun invoke(throwable: Throwable): DisplayError = when (throwable) {
        is NoInternetException, is SocketTimeoutException, is NoRouteToHostException -> {
            DisplayError(
                throwable = throwable,
                headline = application.getString(R.string.error_no_internet_message_title),
                message = application.getString(R.string.error_no_internet_message_message),
                technicalMessage = throwable.message,
                url = (throwable as? NoInternetException)?.url,
                nonRecoverable = false,
                unableToTriage = false,
            )
        }
        is FeatureNotSupportedException -> DisplayError(
            throwable = throwable,
            headline = application.getString(R.string.error_feature_not_supported_title),
            message = application.getString(
                R.string.error_feature_not_supported_message,
                throwable.ciType.name,
                throwable.ciType.name,
            ),
            technicalMessage = throwable.msg,
            nonRecoverable = true,
            unableToTriage = false,
        )
        is HttpException -> {
            val (title, message) = httpErrorMessageMapper(throwable.code())
            DisplayError(
                throwable = throwable,
                headline = application.getString(title),
                message = application.getString(message),
                httpResponseCode = throwable.code(),
                url = throwable.response()?.raw()?.request()?.url().toString(),
                httpResponse = throwable.response()?.errorBody()?.string(),
                technicalMessage = throwable.message,
                nonRecoverable = false,
                unableToTriage = false,
            )
        }
        is JsonDataException -> DisplayError(
            throwable = throwable,
            headline = application.getString(R.string.error_json_parsing_title),
            message = application.getString(R.string.error_json_parsing_message),
            technicalMessage = throwable.message,
            nonRecoverable = true,
            unableToTriage = false,
        )
        else -> DisplayError(
            throwable = throwable,
            headline = application.getString(R.string.error_unknown_title),
            message = application.getString(R.string.error_unknown_message),
            technicalMessage = throwable.message,
            nonRecoverable = false,
            unableToTriage = true,
        )
    }
}
