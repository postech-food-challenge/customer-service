package br.com.fiap.postech.application.usecase

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException


class DeactivateCustomerInteract(private val gateway: CustomerGateway) {
    suspend fun deactivate(cpf: String): Boolean {
        gateway.findByCpf(cpf)?.let {
            return gateway.deactivate(cpf)
        } ?: throw CustomerNotFoundException(cpf)
    }
}