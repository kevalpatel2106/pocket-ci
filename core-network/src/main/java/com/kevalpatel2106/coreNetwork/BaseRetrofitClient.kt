package com.kevalpatel2106.coreNetwork

import com.kevalpatel2106.coreNetwork.converter.StringConverter
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class BaseRetrofitClient(private val okHttpFactory: OkHttpClientFactory) {
    private val moshi = Moshi.Builder()
        .apply { addMoshiAdapters(this) }
        .build()

    protected fun getRetrofit(baseUrl: Url, token: Token): Retrofit {
        val modifiedClient = okHttpFactory.getOkHttpClient().newBuilder()
            .apply {
                interceptors(baseUrl, token).forEach(::addInterceptor)
                okHttpFactory.getFlavouredInterceptor().apply {
                    getInterceptors().forEach(::addInterceptor)
                    getNetworkInterceptors().forEach(::addNetworkInterceptor)
                }
            }
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl.value)
            .client(modifiedClient)
            .addConverterFactory(StringConverter.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    protected fun <T> getService(baseUrl: Url, token: Token, service: Class<T>): T {
        return getRetrofit(baseUrl, token).create(service)
    }

    abstract fun interceptors(baseUrl: Url, token: Token): List<Interceptor>

    open fun addMoshiAdapters(moshiBuilder: Moshi.Builder): Moshi.Builder = moshiBuilder
}
