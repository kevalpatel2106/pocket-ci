package com.kevalpatel2106.coreTest

import com.flextrade.kfixture.KFixture
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun buildHttpException(code: Int, fixture: KFixture): Throwable {
    return HttpException(Response.error<String>(code, fixture<String>().toResponseBody(null)))
}
