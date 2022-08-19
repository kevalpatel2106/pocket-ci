package com.kevalpatel2106.coreNetwork.okHttp

import android.net.ConnectivityManager
import com.kevalpatel2106.entity.exception.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

internal class NetworkConnectionInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isConnected()) {
            Timber.i("No internet connection!")
            throw NoInternetException(url = request.url.toUrl().toString())
        } else {
            return chain.proceed(chain.request())
        }
    }

    @Suppress("MissingPermission")
    private fun isConnected(): Boolean {
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
