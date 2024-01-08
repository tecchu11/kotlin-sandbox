package com.example

import com.examples.helloworld.GreeterGrpcKt
import com.examples.helloworld.HelloReply
import com.examples.helloworld.HelloRequest
import com.examples.helloworld.helloReply
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import kotlinx.coroutines.delay
import mu.KotlinLogging
import java.util.concurrent.TimeUnit

val logger = KotlinLogging.logger {}
private const val TERMINATION_PERIOD = 10L

fun main() {
    val svr = ServerBuilder
        .forPort(9000)
        .addService(Greeter())
        .addService(ProtoReflectionService.newInstance())
        .build()
    svr.start().also {
        logger.info { "server started with ${it.port}" }
        Runtime.getRuntime().addShutdownHook(
            Thread {
                logger.info { "*** shutting down gRPC server since JVM is shutting down ***" }
                it.shutdown().awaitTermination(TERMINATION_PERIOD, TimeUnit.SECONDS)
                logger.info { "*** server shut down ***" }
            }.apply {
                this.name = "shutdown-hook"
            }
        )
    }
    svr.awaitTermination()
}

class Greeter : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply {
        return helloReply {
            delay(1000)
            this.message = "Hello ${request.name}"
        }
    }
}
