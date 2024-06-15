package br.com.fiap.postech.application.usecase

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.domain.exceptions.CustomerAlreadyRegisteredException

class RegisterCustomerInteract(private val gateway: CustomerGateway) {
    suspend fun registerCustomer(customer: Customer) =
        gateway.findByCpf(customer.cpf.value)?.let {
            throw CustomerAlreadyRegisteredException(customer.cpf.value)
        } ?: gateway.create(customer)
}