package com.kevalpatel2106.coreNetwork.okHttp

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientFactory {

    internal val client: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            // Set timeouts
            readTimeout(OkHttpConfig.READ_TIMEOUT, TimeUnit.MINUTES)
            writeTimeout(OkHttpConfig.WRITE_TIMEOUT, TimeUnit.MINUTES)
            connectTimeout(OkHttpConfig.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        }.build()
    }
}
