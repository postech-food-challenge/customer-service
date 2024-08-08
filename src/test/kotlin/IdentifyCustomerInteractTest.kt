import br.com.fiap.postech.application.gateway.CustomerGateway
import br.com.fiap.postech.application.usecase.IdentifyCustomerInteract
import br.com.fiap.postech.domain.entities.Customer
import br.com.fiap.postech.domain.exceptions.CustomerNotActiveException
import br.com.fiap.postech.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.domain.value_objects.CPF
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IdentifyCustomerInteractTest {

    private lateinit var interact : IdentifyCustomerInteract
    private lateinit var gateway: CustomerGateway

    @BeforeEach
    fun setUp() {
        gateway = mockk()
        interact = IdentifyCustomerInteract(gateway)
    }

    @Test
    fun `identify should return customer when customer exists and is active`() = runBlocking {
        val cpfString = "12345678900"
        val customer = Customer(CPF(cpfString), "John Doe", "john.doe@example.com", "555-1234", null, true)

        coEvery { gateway.findByCpf(cpfString) } returns customer

        val result = interact.identify(cpfString)

        Assertions.assertEquals(customer, result)
        coVerify(exactly = 1) { gateway.findByCpf(cpfString) }
    }

    @Test
    fun `identify should throw CustomerNotFoundException when customer does not exist`() = runBlocking {
        val cpfString = "12345678900"

        coEvery { gateway.findByCpf(cpfString) } returns null

        val exception = Assertions.assertThrows(CustomerNotFoundException::class.java) {
            runBlocking { interact.identify(cpfString) }
        }

        Assertions.assertEquals("Customer with CPF: $cpfString not found.", exception.message)
        coVerify(exactly = 1) { gateway.findByCpf(cpfString) }
    }

    @Test
    fun `identify should throw CustomerNotActiveException when customer is inactive`() = runBlocking {
        val cpfString = "12345678900"
        val customer = Customer(CPF(cpfString), "John Doe", "john.doe@example.com", "555-1234", null, false)

        coEvery { gateway.findByCpf(cpfString) } returns customer

        val exception = Assertions.assertThrows(CustomerNotActiveException::class.java) {
            runBlocking { interact.identify(cpfString) }
        }

        Assertions.assertEquals("Customer with CPF: $cpfString is not active.", exception.message)
        coVerify(exactly = 1) { gateway.findByCpf(cpfString) }
    }


    @Test
    fun `identify should correctly create CPF object from string`() = runBlocking {
        val cpfString = "12345678900"

        val cpf = CPF(cpfString)

        Assertions.assertEquals(cpfString, cpf.value)
        coVerify(exactly = 0) { gateway.findByCpf(any()) }
    }

}