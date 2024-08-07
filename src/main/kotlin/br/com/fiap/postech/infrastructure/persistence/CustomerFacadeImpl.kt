package br.com.fiap.postech.infrastructure.persistence

import br.com.fiap.postech.configuration.DatabaseSingleton.dbQuery
import br.com.fiap.postech.domain.exceptions.DatabaseOperationException
import br.com.fiap.postech.infrastructure.persistence.entity.AddressEntity
import br.com.fiap.postech.infrastructure.persistence.entity.Addresses
import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity
import br.com.fiap.postech.infrastructure.persistence.entity.Customers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerFacadeImpl : CustomerFacade {
    private fun resultRowToProductEntity(row: ResultRow?): CustomerEntity? {
        return if (row == null) {
            null
        } else {
            val addressId: Long? = row[Customers.addressId]

            val addressEntity = addressId?.let {
                transaction {
                    Addresses.select { Addresses.id eq addressId }
                        .map {
                            AddressEntity(
                                street = it[Addresses.street],
                                city =  it[Addresses.city],
                                number = it[Addresses.number],
                                district = it[Addresses.district],
                                zipCode = it[Addresses.zipCode]
                            )
                        }.singleOrNull()
                }
            }

            CustomerEntity(
                cpf = row[Customers.cpf],
                name = row[Customers.name],
                email = row[Customers.email],
                cellphone =  row[Customers.cellphone],
                address = addressEntity,
                active = row[Customers.active]
            )
        }
    }

    override suspend fun findByCpf(cpf: String): CustomerEntity? = dbQuery {
        Customers
            .select { (Customers.cpf eq cpf) }
            .map(::resultRowToProductEntity)
            .singleOrNull()
    }

    override suspend fun create(customer: CustomerEntity): CustomerEntity = dbQuery {
        val createdAddressId: Long? = customer.address?.let { address ->
            Addresses.insertAndGetId {
                it[street] = address.street
                it[city] = address.city
                it[district] = address.district
                it[zipCode] = address.zipCode
                it[number] = address.number
            }.value
        }
        Customers.insert {
            it[cpf] = customer.cpf
            it[name] = customer.name
            it[email] = customer.email
            it[cellphone] = customer.cellphone
            it[active] = customer.active
            it[addressId] = createdAddressId
        }
        customer
    } ?: throw DatabaseOperationException("Failed to insert product")

    override suspend fun deactivate(cpf: String): Boolean = dbQuery {
        transaction {
            val updatedRows = Customers.update({ (Customers.cpf eq cpf) and (Customers.active eq true) }) {
                it[active] = false
            }
            updatedRows == 1
        }
    } == true
}