package controller

import fixtures.DataFixtureFactory
import org.junit.jupiter.api.*
import org.mockito.Mockito.mock
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.controller.OrderController
import vitorsb.project.logidataprocess.repository.OrderProductRelationRepository
import vitorsb.project.logidataprocess.repository.OrderRepository
import vitorsb.project.logidataprocess.repository.ProductRepository
import vitorsb.project.logidataprocess.repository.UserRepository
import vitorsb.project.logidataprocess.service.OrderService
import vitorsb.project.logidataprocess.service.ProductService
import vitorsb.project.logidataprocess.service.UserService
import java.io.File

class OrderControllerTest {

    private val userRepository = mock(UserRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val orderRepository = mock(OrderRepository::class.java)
    private val orderProductRelationRepository =
        mock(OrderProductRelationRepository::class.java)

    private lateinit var productService: ProductService
    private lateinit var orderService: OrderService
    private lateinit var userService: UserService

    private lateinit var orderController: OrderController

    @BeforeEach
    fun setUp() {
        productService = ProductService(productRepository)
        userService = UserService(userRepository)
        orderService = OrderService(orderRepository, orderProductRelationRepository, userService, productService)
        orderController = OrderController(orderService)
    }


    @Test
    fun `must return UserTxtFileResponseDTO list response with one user, one order and three products`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-one-user-with-one-order-and-three-products.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-one-user-with-one-order-and-three-products.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = orderController.processTxtFile(file)

        // then
        Assertions.assertEquals(
            ResponseEntity.ok(
                DataFixtureFactory.ValidDataResponses.validOneUserWithOneOrderAndThreeProducts()
            ),
            response
        )
    }

    @Test
    fun `must return invalid file type`() {
        // given
        val dataFile = File("src/test/resources/data/invalid-test-type-file.csv")
        val file: MultipartFile = MockMultipartFile(
            "test.csv",
            "invalid-test-type-file.csv",
            "text/csv",
            dataFile.inputStream()
        )


        try {
            // when
            orderController.processTxtFile(file)
            fail("it should return an invalid file type")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("M=processTxtFile - File The file must be text type", e.message)
        }
    }
}