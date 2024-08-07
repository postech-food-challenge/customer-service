package br.com.fiap.postech.infrastructure.controller

import br.com.fiap.postech.domain.entities.Address
import kotlinx.serialization.Serializable

@Serializable
data class RegisterCustomerRequest(val cpf: String, val name: String, val email: String, val cellphone: String, val address: Address?)
