package com.example.transport.grpc.interceptor

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.kotlin.CoroutineContextServerInterceptor
import kotlinx.coroutines.slf4j.MDCContext
import kotlin.coroutines.CoroutineContext

/**
 * ContextualCoroutineServerInterceptor insert contextual data into CoroutineContext just like [MDCContext].
 *
 * Note that you cannot update MDC context from inside the coroutine simply using MDC.put.
 * These updates are going to be lost on the next suspension and reinstalled to the MDC context that was captured or explicitly specified in contextMap when this object was created on the next resumption.
 * ```kotlin
 *      suspend fun insertNewMDC() {
 *          MDC.put("foo", "bar")
 *          withContext(MDCContext()) {
 *              logger.info { "printtable foo=bar with parent MDCContext" }
 *          }
 *          logger.info { "no printable foo=bar with parent MDCContext" }
 *      }
 * ```
 */
class ContextualCoroutineServerInterceptor : CoroutineContextServerInterceptor() {
    override fun coroutineContext(call: ServerCall<*, *>, headers: Metadata): CoroutineContext {
        return MDCContext(mapOf("service" to call.methodDescriptor.fullMethodName))
    }
}
