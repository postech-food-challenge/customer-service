package br.com.fiap.postech.configuration

import br.com.fiap.postech.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.domain.exceptions.CustomerNotActiveException
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.domain.exceptions.ExceptionResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.security.InvalidParameterException

fun Application.configureExceptionsResponse() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when (throwable) {
                is CustomerNotFoundException  ->
                    call.respond(
                        HttpStatusCode.OK,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.OK.value)
                    )
                is CustomerAlreadyRegisteredException  ->
                    call.respond(
                        HttpStatusCode.OK,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.OK.value)
                    )
                is CustomerNotActiveException  ->
                    call.respond(
                        HttpStatusCode.OK,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.OK.value)
                    )
                is InvalidParameterException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.BadRequest.value)
                    )
            }
        }
    }
}