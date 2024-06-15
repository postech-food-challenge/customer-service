package br.com.fiap.postech.infrastructure.gateway

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.infrastructure.persistence.CustomerFacade
import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity

class CustomerGatewayImpl(private val facade: CustomerFacade) : CustomerGateway {

    override suspend fun findByCpf(cpf: String) = facade.findByCpf(cpf)?.let { entity -> Customer.fromEntity(entity) }

    override suspend fun create(customer: Customer): Customer {
        CustomerEntity.fromDomain(customer).let { entity ->
            val savedEntity = facade.create(entity)
            return Customer.fromEntity(savedEntity)
        }
    }
}