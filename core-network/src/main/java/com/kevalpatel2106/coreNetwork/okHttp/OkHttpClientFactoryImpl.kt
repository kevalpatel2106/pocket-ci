package com.kevalpatel2106.coreNetwork.okHttp

import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class OkHttpClientFactoryImpl @Inject internal constructor(
    private val connectivityManager: ConnectivityManager,
    private val flavouredInterceptor: FlavouredInterceptor,
) : OkHttpClientFactory {

    override fun getFlavouredInterceptor() = flavouredInterceptor

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(OkHttpConfig.READ_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(OkHttpConfig.WRITE_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(OkHttpConfig.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            // Not injecting through constructor to keep NetworkConnectionInterceptor internal
            .addInterceptor(NetworkConnectionInterceptor(connectivityManager))
            .build()
    }

    override fun getOkHttpClient() = client
}
