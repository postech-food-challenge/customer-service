package br.com.fiap.postech.domain.exceptions

class CustomerNotActiveException(cpf: String) : RuntimeException("Customer with CPF: $cpf is not active.") {
}