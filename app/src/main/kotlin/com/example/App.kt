package com.example

import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.SocketTimeoutException

val logger = KotlinLogging.logger {}

fun main() {
    val httpbinClient = Retrofit.Builder()
        .baseUrl("https://httpbin.org/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(EmptyResponseInterceptor())
                .build()
        )
        .addConverterFactory(
            JacksonConverterFactory.create(
                jacksonMapperBuilder().build()
            )
        )
        .build()
        .create(HttpbinClient::class.java)

    runBlocking {
        kotlin.runCatching {
            httpbinClient.statusCodeWith(503)
        }.onFailure {
            when (it.javaClass) {
                HttpException::class.java -> logger.error(it) { "STATUS ERROR" }
                SocketTimeoutException::class.java -> logger.error(it) { "TIMEOUT" }
                else -> logger.error { "UNHANDLED ERROR" }
            }
        }.onSuccess {
            logger.info { "success to call" }
        }
    }
    logger.info { "finish" }
}
