package br.com.fiap.postech.infrastructure.persistence

import br.com.fiap.postech.configuration.DatabaseSingleton.dbQuery
import br.com.fiap.postech.domain.exceptions.DatabaseOperationException
import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity
import br.com.fiap.postech.infrastructure.persistence.entity.Customers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class CustomerFacadeImpl : CustomerFacade {
    private fun resultRowToProductEntity(row: ResultRow?): CustomerEntity? {
        return if (row == null) {
            null
        } else {
            CustomerEntity(
                cpf = row[Customers.cpf],
                name = row[Customers.name],
                email = row[Customers.email]
            )
        }
    }

    override suspend fun findByCpf(cpf: String): CustomerEntity? = dbQuery {
        Customers
            .select { Customers.cpf eq cpf }
            .map(::resultRowToProductEntity)
            .singleOrNull()
    }

    override suspend fun create(customer: CustomerEntity): CustomerEntity = dbQuery {
        Customers.insert {
            it[cpf] = customer.cpf
            it[name] = customer.name
            it[email] = customer.email
        }
        customer
    } ?: throw DatabaseOperationException("Failed to insert product")
}