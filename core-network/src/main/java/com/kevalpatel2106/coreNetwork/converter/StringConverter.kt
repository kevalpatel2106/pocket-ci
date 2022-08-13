package com.kevalpatel2106.coreNetwork.converter

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class StringConverter private constructor() : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? = if (type == String::class.java) {
        Converter { it.string() }
    } else {
        null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<Any, RequestBody>? = if (type == String::class.java) {
        Converter { it.toString().toRequestBody(TEXT_PLAIN_MEDIA_TYPE.toMediaType()) }
    } else {
        null
    }

    companion object {
        private const val TEXT_PLAIN_MEDIA_TYPE = "text/plain"

        fun create() = StringConverter()
    }
}
