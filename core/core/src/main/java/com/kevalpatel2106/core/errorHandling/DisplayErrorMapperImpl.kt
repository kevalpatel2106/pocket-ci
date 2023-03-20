package com.kevalpatel2106.core.errorHandling

import android.app.Application
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import com.kevalpatel2106.entity.exception.NoInternetException
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.util.Date
import javax.inject.Inject

internal class DisplayErrorMapperImpl @Inject constructor(
    private val application: Application,
    private val httpErrorMessageMapper: HttpErrorMessageMapper,
) : DisplayErrorMapper {

    override operator fun invoke(throwable: Throwable, shortMessage: Boolean, time: Date) =
        when (throwable) {
            is NoInternetException, is SocketTimeoutException, is NoRouteToHostException -> {
                DisplayError(
                    throwable = throwable,
                    headline = application.getString(R.string.error_no_internet_title),
                    message = application.getString(R.string.error_no_internet_message),
                    technicalMessage = throwable.message,
                    url = (throwable as? NoInternetException)?.url,
                    time = time,
                    nonRecoverable = false,
                    unableToTriage = false,
                )
            }
            is FeatureNotSupportedException -> mapFeatureNotSupported(throwable, time)
            is HttpException -> mapHttpException(throwable, time)
            is JsonDataException -> mapJsonDataException(throwable, time)
            else -> mapNotTriageException(shortMessage, throwable, time)
        }

    private fun mapNotTriageException(
        shortMessage: Boolean,
        throwable: Throwable,
        time: Date,
    ): DisplayError {
        val msgRes = if (shortMessage) {
            R.string.error_unknown_message_short
        } else {
            R.string.error_unknown_message
        }
        return DisplayError(
            throwable = throwable,
            headline = application.getString(R.string.error_unknown_title),
            time = time,
            message = application.getString(msgRes),
            technicalMessage = throwable.message,
            nonRecoverable = throwable is IllegalStateException,
            unableToTriage = true,
        )
    }

    private fun mapJsonDataException(throwable: Throwable, time: Date) = DisplayError(
        throwable = throwable,
        headline = application.getString(R.string.error_json_parsing_title),
        message = application.getString(R.string.error_json_parsing_message),
        time = time,
        technicalMessage = throwable.message,
        nonRecoverable = true,
        unableToTriage = false,
    )

    private fun mapHttpException(throwable: HttpException, time: Date): DisplayError {
        val (title, message) = httpErrorMessageMapper(throwable.code())
        return DisplayError(
            throwable = throwable,
            headline = application.getString(title),
            message = application.getString(message),
            httpResponseCode = throwable.code(),
            url = throwable.response()?.raw()?.request()?.url().toString(),
            httpResponse = throwable.response()?.errorBody()?.string(),
            technicalMessage = throwable.message,
            time = time,
            nonRecoverable = false,
            unableToTriage = false,
        )
    }

    private fun mapFeatureNotSupported(
        throwable: FeatureNotSupportedException,
        time: Date,
    ) = DisplayError(
        throwable = throwable,
        headline = application.getString(R.string.error_feature_not_supported_title),
        message = application.getString(
            R.string.error_feature_not_supported_message,
            throwable.ciType.name,
            throwable.ciType.name,
        ),
        time = time,
        technicalMessage = throwable.msg,
        nonRecoverable = true,
        unableToTriage = false,
    )
}
