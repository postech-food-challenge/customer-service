package br.com.fiap.postech.application.usecase

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.domain.exceptions.CustomerNotActiveException
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.domain.value_objects.CPF

class IdentifyCustomerInteract(private val gateway: CustomerGateway) {
    suspend fun identify(cpfString: String): Customer {
        val cpf = CPF(cpfString)
        val customer = gateway.findByCpf(cpf.value)
        if (customer == null)
            throw CustomerNotFoundException(cpf.value)
        else
            if (!customer.active)
                throw CustomerNotActiveException(cpf.value)
        return customer;
//        return gateway.findByCpf(cpf.value) ?: throw CustomerNotFoundException(cpf.value)
    }
}