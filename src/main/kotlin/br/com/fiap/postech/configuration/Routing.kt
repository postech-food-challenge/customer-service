package br.com.fiap.postech.configuration

import br.com.fiap.postech.infrastructure.controller.createProductRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        createProductRoute()
    }
}
