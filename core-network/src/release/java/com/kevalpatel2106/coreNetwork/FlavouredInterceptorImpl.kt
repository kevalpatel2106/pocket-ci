package com.kevalpatel2106.coreNetwork

import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import okhttp3.Interceptor
import javax.inject.Inject

internal class FlavouredInterceptorImpl @Inject constructor() : FlavouredInterceptor {
    override fun getInterceptors(): List<Interceptor> = emptyList()
    override fun getNetworkInterceptors(): List<Interceptor> = emptyList()
}
