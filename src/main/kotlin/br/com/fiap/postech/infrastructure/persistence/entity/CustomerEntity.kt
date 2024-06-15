package br.com.fiap.postech.infrastructure.persistence.entity

import br.com.fiap.postech.domain.entities.Customer
import org.jetbrains.exposed.dao.id.LongIdTable

data class CustomerEntity(
    val cpf: String,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(domain: Customer): CustomerEntity {
            return CustomerEntity(
                cpf = domain.cpf.value,
                name = domain.name,
                email = domain.email
            )
        }
    }
}

object Customers : LongIdTable() {
    val cpf = varchar("cpf", 50).uniqueIndex()
    val name = varchar("name", 50)
    val email = varchar("email", 1024)
}