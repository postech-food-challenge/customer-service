package br.com.fiap.postech.domain.value_objects

import br.com.fiap.postech.domain.exceptions.InvalidCpfException

data class CPF(val value: String) {
    init {
        validateCpf(value)
    }

    private fun validateCpf(cpf: String) {
        if (cpf.length != 11 || !cpf.all { it.isDigit() }) {
            throw InvalidCpfException("Invalid CPF format. Expected an 11-digit number.")
        }
    }
}
