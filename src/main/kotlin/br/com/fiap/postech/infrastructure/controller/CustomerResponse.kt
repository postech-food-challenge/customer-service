package br.com.fiap.postech.infrastructure.controller

import br.com.fiap.postech.domain.entities.Address
import br.com.fiap.postech.domain.entities.Customer
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse (
    val cpf: String,
    val name: String,
    val email: String,
    val cellphone: String,
    val address: Address?,
    val active: Boolean
) {
    companion object {
        fun fromDomain(domain: Customer) = CustomerResponse(domain.cpf.value, domain.name, domain.email, domain.cellphone, domain.address, domain.active)
    }
}
