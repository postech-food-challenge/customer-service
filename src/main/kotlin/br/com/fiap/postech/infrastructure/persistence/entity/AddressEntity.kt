package br.com.fiap.postech.infrastructure.persistence.entity

import br.com.fiap.postech.domain.entities.Address
import org.jetbrains.exposed.dao.id.LongIdTable

data class AddressEntity(
    val street : String,
    val city : String,
    val district  : String,
    val number : String,
    val zipCode: String
) {
    companion object {
        fun fromDomain(domain: Address): AddressEntity {
            return AddressEntity(
                street = domain.street,
                city =  domain.city,
                district = domain.district,
                number = domain.number,
                zipCode = domain.zipCode
            )
        }
    }
}

object Addresses : LongIdTable() {
    val street = varchar("street", 244)
    val city = varchar("city", 244)
    val district = varchar("district", 244)
    val number = varchar("number", 10)
    val zipCode = varchar("zipCode", 20)
}