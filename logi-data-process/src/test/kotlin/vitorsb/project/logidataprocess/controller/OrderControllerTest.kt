package vitorsb.project.logidataprocess.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.fixtures.DtoFixtureFactory
import java.io.File

@SpringBootTest
class OrderControllerTest {
    @Autowired
    private lateinit var orderController: OrderController

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
                DtoFixtureFactory.ValidDtos.validOneUserWithOneOrderAndThreeProducts()
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