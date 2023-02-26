package com.kevalpatel2106.coreNetwork

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

internal class FlavouredInterceptorImpl @Inject constructor(
    private val networkFlipperPlugin: NetworkFlipperPlugin,
) : FlavouredInterceptor {
    override fun getInterceptors(): List<Interceptor> {
        return listOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }

    override fun getNetworkInterceptors(): List<Interceptor> {
        return listOf(FlipperOkhttpInterceptor(networkFlipperPlugin))
    }
}
