package br.com.fiap.postech.application.gateway

import br.com.fiap.postech.domain.entities.Customer

interface CustomerGateway {
    suspend fun findByCpf(cpf: String): Customer?
    suspend fun create(customer: Customer): Customer
    suspend fun deactivate(cpf: String): Boolean
}