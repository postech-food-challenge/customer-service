import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.application.usecase.DeactivateCustomerInteract
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.domain.value_objects.CPF
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeactivateCustomerInteractTest {

    private lateinit var interact : DeactivateCustomerInteract
    private lateinit var gateway: CustomerGateway

    @BeforeEach
    fun setUp() {
        gateway = mockk()
        interact = DeactivateCustomerInteract(gateway)
    }

    @Test
    fun `deactivate should return success response when customer is deactivated`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "John Doe", "john.doe@example.com", "555-1234", null, true)

        coEvery { gateway.findByCpf(cpf) } returns customer
        coEvery {gateway.deactivate(cpf)} returns true

        val response = interact.deactivate(cpf)

        Assertions.assertEquals(true, response.success)
        Assertions.assertEquals("User unregistered successfully.", response.message)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 1) { gateway.deactivate(cpf) }
    }

    @Test
    fun `deactivate should throw CustomerNotFoundException when customer is not found`() = runBlocking {
        val cpf = "12345678900"

        coEvery { gateway.findByCpf(cpf) } returns null

        val exception = Assertions.assertThrows(CustomerNotFoundException::class.java) {
            runBlocking { interact.deactivate(cpf) }
        }

        Assertions.assertEquals("Customer with CPF: $cpf not found.", exception.message)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 0) { gateway.deactivate(cpf) }
    }

    @Test
    fun `deactivate should return failure response when customer cannot be deactivated`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "John Doe", "john.doe@example.com", "555-1234", null, true)

        coEvery { gateway.findByCpf(cpf) } returns customer
        coEvery { gateway.deactivate(cpf) } returns false

        val response = interact.deactivate(cpf)

        Assertions.assertEquals(false, response.success)
        Assertions.assertEquals("User could not be unregistered.", response.message)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 1) { gateway.deactivate(cpf) }
    }

    @Test
    fun `deactivate should throw CustomerNotFoundException with correct CPF in message`() = runBlocking {
        val cpf = "12345678900"

        coEvery { gateway.findByCpf(cpf) } returns null

        val exception = Assertions.assertThrows(CustomerNotFoundException::class.java) {
            runBlocking { interact.deactivate(cpf) }
        }

        Assertions.assertTrue(exception.message!!.contains(cpf))

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 0) { gateway.deactivate(cpf) }
    }

    @Test
    fun `deactivate should throw exception when gateway throws exception`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "John Doe", "john.doe@example.com", "555-1234", null, true)

        coEvery { gateway.findByCpf(cpf) } returns customer
        coEvery { gateway.deactivate(cpf) } throws RuntimeException("Unexpected error")

        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            runBlocking { interact.deactivate(cpf) }
        }

        Assertions.assertEquals("Unexpected error", exception.message)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 1) { gateway.deactivate(cpf) }
    }

    @Test
    fun `deactivate should return failure response when customer is already inactive`() = runBlocking {
        val cpf = "12345678900"
        val customer = Customer(CPF(cpf), "John Doe", "john.doe@example.com", "555-1234", null, false) // Cliente já inativo

        coEvery { gateway.findByCpf(cpf) } returns customer
        coEvery { gateway.deactivate(cpf) } returns false

        val response = interact.deactivate(cpf)

        Assertions.assertEquals(false, response.success)
        Assertions.assertEquals("User could not be unregistered.", response.message)

        coVerify(exactly = 1) { gateway.findByCpf(cpf) }
        coVerify(exactly = 1) { gateway.deactivate(cpf) }
    }
}