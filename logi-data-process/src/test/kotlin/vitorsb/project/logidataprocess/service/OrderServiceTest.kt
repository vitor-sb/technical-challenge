package vitorsb.project.logidataprocess.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import vitorsb.project.logidataprocess.fixtures.DataFixtureFactory
import java.io.File

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private lateinit var orderService: OrderService

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
        val response = orderService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validOneUserWithOneOrderAndThreeProducts(), response)
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
        val response = orderService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validOneUserWithTwoOrdersAndProducts(), response)
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
        val response = orderService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validThreeUsersWithOrderAndProduct(), response)
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
        val response = orderService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validTwoUsersWithOrderAndProduct(), response)
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
            orderService.processTxtFile(file)
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
            orderService.processTxtFile(file)
            fail("it should return an invalid number of characters error")
        } catch (e: Exception) {
            // then
            val expectedErrorMessage = "M=processTxtFile - Error: invalid line length (line: " +
                    "0000000088                             Terra Daniel DDS00000008360000000003     1899)"
            assertEquals(expectedErrorMessage, e.message)
        }
    }
}