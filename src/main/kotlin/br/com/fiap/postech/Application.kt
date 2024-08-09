package br.com.fiap.postech

import br.com.fiap.postech.configuration.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init(environment.config, log)
    CryptoSingleton.init(environment.config)
    configureKoin()
    configureSerialization()
    configureRouting()
    configureExceptionsResponse()
}
