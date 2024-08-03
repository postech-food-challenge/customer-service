package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.application.usecase.DeactivateCustomerInteract
import br.com.fiap.postech.application.usecase.IdentifyCustomerInteract
import br.com.fiap.postech.application.usecase.RegisterCustomerInteract
import br.com.fiap.postech.infrastructure.gateway.CustomerGatewayImpl
import br.com.fiap.postech.infrastructure.persistence.CustomerFacade
import br.com.fiap.postech.infrastructure.persistence.CustomerFacadeImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(appModules)
    }
}

val appModules = module {
    single<CustomerFacade> { CustomerFacadeImpl() }
    single<CustomerGateway> { CustomerGatewayImpl(get()) }
    single { RegisterCustomerInteract(get()) }
    single { IdentifyCustomerInteract(get()) }
    single { DeactivateCustomerInteract(get()) }

}
