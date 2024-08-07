package br.com.fiap.postech.infrastructure.persistence

import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity

interface CustomerFacade {
    suspend fun findByCpf(cpf: String): CustomerEntity?
    suspend fun create(customer: CustomerEntity): CustomerEntity
    suspend fun deactivate(cpf: String): Boolean
}
