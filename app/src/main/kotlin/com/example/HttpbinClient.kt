package com.example

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface HttpbinClient {
    @POST("/status/{statusCode}")
    suspend fun statusCodeWith(@Path("statusCode") code: Int)
}

class EmptyResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            return response
        }
        if (response.code() != 204 && response.code() != 205) {
            return response
        }
        if ((response.body()?.contentLength() ?: -1) >= 0) {
            return response
        }
        val emptyBody = ResponseBody.create(MediaType.get("application/json"), "")
        return response
            .newBuilder()
            .code(200)
            .body(emptyBody)
            .build()
    }
}

fun <T> Response<T>.getOrElse(wrap: (cause: Exception) -> T): T? {
    if (this.isSuccessful.not()) wrap(HttpException(this))
    return this.body()
}
