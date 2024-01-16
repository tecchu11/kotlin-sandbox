package com.example.transport.grpc

import com.example.transport.grpc.interceptor.ContextualCoroutineServerInterceptor
import com.example.transport.grpc.service.HelloService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.ServerInterceptors
import io.grpc.protobuf.services.HealthStatusManager
import io.grpc.protobuf.services.ProtoReflectionService
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.environmentProperties
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger { }

/**
 * Application manages gRPC application.
 * You must register components at init phase, and then any components are injected by [KoinComponent.inject].
 *
 * If you customize application behavior, specify [ApplicationConfiguration].
 */
class Application(
    private val config: ApplicationConfiguration = ApplicationConfiguration()
) : KoinComponent {

    init {
        module {
            single { ContextualCoroutineServerInterceptor() }
            single { HelloService() }
        }.let {
            startKoin {
                environmentProperties()
                modules(it)
            }
        }
    }

    private lateinit var svr: Server
    private val contextualCoroutineServerInterceptor: ContextualCoroutineServerInterceptor by inject()
    private val helloService: HelloService by inject()

    /**
     * Start [Application] with given config. After starting application, use [block].
     */
    fun start(): Application {
        svr = ServerBuilder.forPort(config.port)
            .addService(HealthStatusManager().healthService)
            .addService(ProtoReflectionService.newInstance())
            .addService(ServerInterceptors.intercept(helloService, contextualCoroutineServerInterceptor))
            .build()
            .start()
            .also {
                logger.info { "Application started with ${config.port}" }
                Runtime.getRuntime().addShutdownHook(
                    Thread {
                        logger.info { "*** shutting down gRPC server since JVM is shutting down ***" }
                        it.shutdown().awaitTermination(config.terminationPeriod, TimeUnit.SECONDS)
                        logger.info { "*** server shut down ***" }
                    }.apply {
                        this.name = "shutdown-hook"
                    }
                )
            }
        return this
    }

    /**
     * block waits for the [Application] to become terminated.
     */
    fun block() {
        svr.awaitTermination()
    }
}

/**
 * [ApplicationConfiguration] manages application configuration just like
 *
 * @property port Listen port of gRPC application. Default is 9000.
 * @property terminationPeriod Period(unit is sec) for application become terminated. Default is 10
 */
data class ApplicationConfiguration(
    val port: Int = 8080,
    val terminationPeriod: Long = 10,
)
