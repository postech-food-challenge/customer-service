package br.com.fiap.postech.infrastructure.persistence

import br.com.fiap.postech.configuration.DatabaseSingleton.dbQuery
import br.com.fiap.postech.domain.exceptions.DatabaseOperationException
import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity
import br.com.fiap.postech.infrastructure.persistence.entity.Customers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CustomerFacadeImpl : CustomerFacade {
    private fun resultRowToProductEntity(row: ResultRow?): CustomerEntity? {
        return if (row == null) {
            null
        } else {
            CustomerEntity(
                cpf = row[Customers.cpf],
                name = row[Customers.name],
                email = row[Customers.email],
                cellphone =  row[Customers.cellphone],
                address = row[Customers.address],
                active = row[Customers.active]
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
            it[cellphone] = customer.cellphone
            it[address] = customer.address
            it[active] = customer.active
        }
        customer
    } ?: throw DatabaseOperationException("Failed to insert product")

    override suspend fun deactivate(cpf: String): Boolean = dbQuery {
        transaction {
            val updatedRows = Customers.update({ Customers.cpf eq cpf }) {
                it[active] = false
            }
            updatedRows == 1
        }
    } == true
}