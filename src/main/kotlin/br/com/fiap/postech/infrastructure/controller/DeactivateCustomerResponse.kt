package br.com.fiap.postech.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
data class DeactivateCustomerResponse (
    val success: Boolean,
    val message: String
)