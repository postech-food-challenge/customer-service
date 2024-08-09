import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.application.usecase.RegisterCustomerInteract
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.domain.value_objects.CPF
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RegisterCustomerInteractTest {

    private lateinit var interact : RegisterCustomerInteract
    private lateinit var gateway: CustomerGateway

    @BeforeEach
    fun setUp() {
        gateway = mockk()
        interact = RegisterCustomerInteract(gateway)
    }

    @Test
    fun `registerCustomer should throw CustomerAlreadyRegisteredException when customer is already registered`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "John Doe", "john.doe@example.com", "555-1234", null, true)

        coEvery { gateway.findByCpf(cpf) } returns customer

        Assertions.assertThrows(CustomerAlreadyRegisteredException::class.java) {
            runBlocking { interact.registerCustomer(customer) }
        }

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 0) { gateway.create(any()) }
    }

    @Test
    fun `registerCustomer should create new customer when customer is not registered`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "Jane Doe", "jane.doe@example.com", "555-5678", null, true)

        coEvery { gateway.findByCpf(cpf) } returns null
        coEvery { gateway.create(customer) } returns customer

        interact.registerCustomer(customer)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 1) { gateway.create(customer) }
    }

}