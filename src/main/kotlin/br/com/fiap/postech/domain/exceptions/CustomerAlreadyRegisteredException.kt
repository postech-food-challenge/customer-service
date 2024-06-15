package br.com.fiap.postech.domain.exceptions

class CustomerAlreadyRegisteredException(cpf: String) :
    RuntimeException("Customer with CPF $cpf is already registered.") {
}
