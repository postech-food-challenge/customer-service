package br.com.fiap.postech.infrastructure.persistence.entity

import br.com.fiap.postech.domain.entities.Customer
import org.jetbrains.exposed.dao.id.LongIdTable

data class CustomerEntity(
    val cpf: String,
    val name: String,
    val email: String,
    val cellphone: String,
    val address: AddressEntity?,
    val active: Boolean
) {
    companion object {
        fun fromDomain(domain: Customer): CustomerEntity {
            return CustomerEntity(
                cpf = domain.cpf.value,
                name = domain.name,
                email = domain.email,
                cellphone = domain.cellphone,
                address =  domain.address ?.let { AddressEntity.fromDomain(domain.address) },
                active = domain.active
            )
        }
    }
}

object Customers : LongIdTable() {
    val cpf = varchar("cpf", 50).uniqueIndex()
    val name = varchar("name", 50)
    val email = varchar("email", 1024)
    val cellphone = varchar("cellphone", 1024)
    val active = bool("active")
    val addressId = long("address_id").references(Addresses.id)

}