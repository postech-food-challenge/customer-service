package br.com.fiap.postech.infrastructure.controller

import br.com.fiap.postech.application.usecase.DeactivateCustomerInteract
import br.com.fiap.postech.application.usecase.IdentifyCustomerInteract
import br.com.fiap.postech.application.usecase.RegisterCustomerInteract
import br.com.fiap.postech.domain.entities.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.security.InvalidParameterException

fun Route.createProductRoute() {
    val registerCustomerInteract: RegisterCustomerInteract by inject()

    post("/v1/customer") {
        val customerRequest = call.receive<RegisterCustomerRequest>()
        val customer = Customer.fromRequest(customerRequest)
        val registeredCustomer = CustomerResponse.fromDomain(registerCustomerInteract.registerCustomer(customer))
        call.response.header(HttpHeaders.Location, "/v1/customer/${registeredCustomer.cpf}")
        call.respond(HttpStatusCode.Created, registeredCustomer)
    }
}

fun Route.identifyCustomerByCpfRoute() {
    val identifyCustomerInteract: IdentifyCustomerInteract by inject()

    get("/v1/customers/{cpf?}") {
        val cpfString = call.parameters["cpf"] ?: throw InvalidParameterException("Missing or malformed id")
        val product = identifyCustomerInteract.identify(cpfString).run { CustomerResponse.fromDomain(this) }
        call.respond(product)
    }
}

fun Route.deactivateCustomerByCpfRoute() {
    val deactivateCustomerInteract: DeactivateCustomerInteract by inject()

    post("/v1/customers/{cpf?}/deactivate") {
        val cpfString = call.parameters["cpf"] ?: throw InvalidParameterException("Missing or malformed id")
        val response = deactivateCustomerInteract.deactivate(cpfString).run { this }
        call.respond(response)
    }
}