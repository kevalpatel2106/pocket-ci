package com.kevalpatel2106.coreNetwork.okHttp

import okhttp3.OkHttpClient

interface OkHttpClientFactory {
    fun getOkHttpClient(): OkHttpClient
    fun getFlavouredInterceptor(): FlavouredInterceptor
}
