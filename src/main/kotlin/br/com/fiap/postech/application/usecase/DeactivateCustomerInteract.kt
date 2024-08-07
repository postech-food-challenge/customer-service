package br.com.fiap.postech.application.usecase

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.infrastructure.controller.DeactivateCustomerResponse


class DeactivateCustomerInteract(private val gateway: CustomerGateway) {
    suspend fun deactivate(cpf: String): DeactivateCustomerResponse {
        gateway.findByCpf(cpf)?.let {
            val success = gateway.deactivate(cpf)
            if (success) {
                return DeactivateCustomerResponse(success, "User unregistered successfully.")
            } else {
                return DeactivateCustomerResponse(success, "User could not be unregistered.")
            }
        } ?: throw CustomerNotFoundException(cpf)
    }
}