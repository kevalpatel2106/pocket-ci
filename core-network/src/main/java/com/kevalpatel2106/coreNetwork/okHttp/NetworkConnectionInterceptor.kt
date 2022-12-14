package com.kevalpatel2106.coreNetwork.okHttp

import com.kevalpatel2106.coreNetwork.usecase.IsNetworkConnectedCheck
import com.kevalpatel2106.entity.exception.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

internal class NetworkConnectionInterceptor @Inject constructor(
    private val isNetworkConnectedCheck: IsNetworkConnectedCheck,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isNetworkConnectedCheck()) {
            Timber.i("No internet connection!")
            throw NoInternetException(url = request.url.toUrl().toString())
        } else {
            return chain.proceed(chain.request())
        }
    }
}
