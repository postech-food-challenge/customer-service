package br.com.fiap.postech.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCustomerRequest(val cpf: String, val name: String, val email: String)
