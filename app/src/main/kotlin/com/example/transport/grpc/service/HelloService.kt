package com.example.transport.grpc.service

import com.example.helloworld.GreeterGrpcKt
import com.example.helloworld.HelloReply
import com.example.helloworld.HelloRequest
import com.example.helloworld.helloReply
import kotlinx.coroutines.delay
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

/**
 * HelloService is implementation of helloworld.Greeter service.
 */
class HelloService : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply {
        logger.info { "Accepted request" }
        delay(1000)
        logger.info { "Calculated done!!" }
        return helloReply {
            this.message = "Hello ${request.name}"
        }
    }
}
