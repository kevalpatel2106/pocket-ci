package com.kevalpatel2106.coreNetwork

import com.kevalpatel2106.coreNetwork.converter.StringConverter
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class BaseRetrofitClient(val okHttpFactory: OkHttpClientFactory) {
    private val moshi = Moshi.Builder()
        .apply { addMoshiAdapters(this) }
        .build()

    protected fun <T> getService(baseUrl: String, token: String?, service: Class<T>): T {
        val modifiedClient = okHttpFactory.client.newBuilder()
            .apply { interceptors(baseUrl, token).forEach(::addInterceptor) }
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(modifiedClient)
            .addConverterFactory(StringConverter.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(service)
    }

    abstract fun interceptors(baseUrl: String, token: String?): List<Interceptor>

    open fun addMoshiAdapters(moshiBuilder: Moshi.Builder): Moshi.Builder = moshiBuilder
}
