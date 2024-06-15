package br.com.fiap.postech

import br.com.fiap.postech.plugins.configureRouting
import br.com.fiap.postech.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
