package br.com.fiap.postech.domain.entities

import br.com.fiap.postech.infrastructure.cipher.CryptoUtil
import br.com.fiap.postech.infrastructure.controller.RegisterCustomerRequest
import br.com.fiap.postech.infrastructure.persistence.entity.AddressEntity
import kotlinx.serialization.Serializable

@Serializable
data class Address(val street: String, val city : String, val district  : String, val number : String, val zipCode: String) {
    companion object {
        fun fromEntity(entityObject: AddressEntity?) =
            entityObject?.let {
                Address(
                    street = CryptoUtil.decrypt(it.street),
                    city = it.city,
                    district = it.district,
                    number = CryptoUtil.decrypt(it.number),
                    zipCode = CryptoUtil.decrypt(it.zipCode)
                )
            }

        fun fromRequest(request: RegisterCustomerRequest) = request.address?.let { Address(street = CryptoUtil.encrypt(it.street), city = it.city, district = it.district, zipCode = it.zipCode, number = it.number) }
    }
}
