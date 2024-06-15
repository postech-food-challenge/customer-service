package br.com.fiap.postech.domain.exceptions

class CustomerNotFoundException(cpf: String) : RuntimeException("Customer with CPF: $cpf not found.") {
}
