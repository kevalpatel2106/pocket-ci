package com.kevalpatel2106.coreNetwork.okHttp

import okhttp3.Interceptor

internal interface FlavouredInterceptor {
    fun getInterceptors(): List<Interceptor>
    fun getNetworkInterceptors(): List<Interceptor>
}
