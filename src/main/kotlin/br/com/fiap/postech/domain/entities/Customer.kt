package br.com.fiap.postech.domain.entities

import br.com.fiap.postech.domain.value_objects.CPF
import br.com.fiap.postech.infrastructure.controller.RegisterCustomerRequest
import br.com.fiap.postech.infrastructure.persistence.entity.CustomerEntity

data class Customer(val cpf: CPF, val name: String, val email: String, val cellphone: String, val address: String, val active: Boolean) {
    companion object {
        fun fromEntity(entityObject: CustomerEntity) =
            Customer(
                cpf = CPF(entityObject.cpf),
                name = entityObject.name,
                email = entityObject.email,
                cellphone =  entityObject.cellphone,
                address = entityObject.address,
                active =  entityObject.active
            )

        fun fromRequest(request: RegisterCustomerRequest) = Customer(CPF(request.cpf), request.name, request.email, request.cellphone, request.address, true)
    }
}
