package vitorsb.project.logidataprocess.service

import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import vitorsb.project.logidataprocess.config.JpaConfig
import vitorsb.project.logidataprocess.fixtures.DtoFixtureFactory
import vitorsb.project.logidataprocess.fixtures.EntityFixtureFactory
import vitorsb.project.logidataprocess.repository.OrderRepository
import vitorsb.project.logidataprocess.repository.UserRepository
import java.io.File

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@Import(JpaConfig::class)
class OrderServiceTest {
    @Autowired
    private lateinit var service: OrderService
    @Autowired
    private lateinit var repository: OrderRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @MockBean
    private lateinit var userService: UserService
    @MockBean
    private lateinit var productService: ProductService

    @BeforeEach
    fun setup() {
        repository.deleteAll()
        userRepository.deleteAll()
    }
    @Test
    fun `must create and return an order`() {
        // given
        val user = userRepository.save(EntityFixtureFactory.ValidEntities.validUser())
        val order = DtoFixtureFactory.ValidDtos.validOrderResponseDTO()

        // when
        val response = service.createOrder(order, user)

        // then
        assertEquals(EntityFixtureFactory.ValidEntities.validOrder(), response)
    }

    @Test
    fun `must return an order by user id and external id`() {
        // given
        val userEntity = userRepository.save(EntityFixtureFactory.ValidEntities.validUser())
        val exceptedOrder = repository.save(EntityFixtureFactory.ValidEntities.validOrder(userEntity.id))

        given(userService.findById(userEntity.id)).willReturn(userEntity)

        given(productService.findByOrderId(exceptedOrder.id!!)).willReturn(
            mutableListOf(
                EntityFixtureFactory.ValidEntities.validProduct(exceptedOrder.id!!)
            )
        )

        // when
        val response = service.findByUserIdAndExternalId(userEntity.id, exceptedOrder.externalId)

        // then
        assertEquals(DtoFixtureFactory.ValidDtos.validOrderResponseDTO(), response)
    }

    @Test
    fun `must return UserTxtFileResponseDTO list with one user, one order and three products`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-one-user-with-one-order-and-three-products.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-one-user-with-one-order-and-three-products.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = service.processTxtFile(file)

        // then
        assertEquals(DtoFixtureFactory.ValidDtos.validOneUserWithOneOrderAndThreeProducts(), response)
    }

    @Test
    fun `must return UserTxtFileResponseDTO list with one user, two orders and products`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-one-user-with-two-ordes-and-products.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-one-user-with-two-ordes-and-products.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = service.processTxtFile(file)

        // then
        assertEquals(DtoFixtureFactory.ValidDtos.validOneUserWithTwoOrdersAndProducts(), response)
    }

    @Test
    fun `must return UserTxtFileResponseDTO list with three users with order and product`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-three-users-with-order-and-product.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-three-users-with-order-and-product.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = service.processTxtFile(file)

        // then
        assertEquals(DtoFixtureFactory.ValidDtos.validThreeUsersWithOrderAndProduct(), response)
    }
    @Test
    fun `must return UserTxtFileResponseDTO list with two users with order and product even with empty line`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-two-users-and-line-empty.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-two-users-and-line-empty.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = service.processTxtFile(file)

        // then
        assertEquals(DtoFixtureFactory.ValidDtos.validTwoUsersWithOrderAndProduct(), response)
    }

    @Test
    fun `should return an empty file error`() {
        // given
        val dataFile = File("src/test/resources/data/invalid-test-empty-file.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "invalid-test-empty-file.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        try {
            service.processTxtFile(file)
            fail("it should return an empty file error")
        } catch (e: Exception) {
            // then
            assertEquals("M=processTxtFile - Error: error reading file", e.message)
        }
    }

    @Test
    fun `should return an invalid number of characters error`() {
        // given
        val dataFile = File("src/test/resources/data/invalid-test-one-user-line-with-invalid-length.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "invalid-test-one-user-line-with-invalid-length.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        try {
            service.processTxtFile(file)
            fail("it should return an invalid number of characters error")
        } catch (e: Exception) {
            // then
            val expectedErrorMessage = "M=processTxtFile - Error: invalid line length (line: " +
                    "0000000088                             Terra Daniel DDS00000008360000000003     1899)"
            assertEquals(expectedErrorMessage, e.message)
        }
    }
}