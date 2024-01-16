package com.example

import com.example.transport.grpc.Application
import mu.KotlinLogging
import kotlin.time.measureTimedValue

private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "Attempt to start application." }
    measureTimedValue {
        Application().start()
    }.let {
        logger.info { "Application has started with ${it.duration}" }
        it.value.block()
    }
}
