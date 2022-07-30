package com.kevalpatel2106.coreNetwork

import com.kevalpatel2106.coreNetwork.moshi.MoshiFactory
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

abstract class BaseRetrofitClient(
    private val okHttpFactory: OkHttpClientFactory,
    moshiFactory: MoshiFactory,
) {
    private val moshi = moshiFactory.getBuilder().apply { addMoshiAdapters(this) }.build()

    @Inject
    internal lateinit var flavouredInterceptor: FlavouredInterceptor

    protected fun <T> getService(baseUrl: String, token: String?, service: Class<T>): T {
        val modifiedClient = okHttpFactory.client.newBuilder()
            .apply { interceptors(baseUrl, token).forEach { addInterceptor(it) } }
            .apply {
                flavouredInterceptor.getInterceptors().forEach(::addInterceptor)
                flavouredInterceptor.getNetworkInterceptors().forEach(::addNetworkInterceptor)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(modifiedClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(service)
    }

    abstract fun interceptors(baseUrl: String, token: String?): List<Interceptor>

    open fun addMoshiAdapters(moshiBuilder: Moshi.Builder): Moshi.Builder = moshiBuilder
}
