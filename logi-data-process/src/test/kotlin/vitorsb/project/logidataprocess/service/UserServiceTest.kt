package service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import fixtures.DataFixtureFactory
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.service.UserService
import java.io.File

class UserServiceTest {
    private val userService: UserService = UserService()
    @Test
    fun `must return UserTxtFileResponseDTO with one user, one order and three products`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-one-user-with-one-order-and-three-products.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-one-user-with-one-order-and-three-products.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = userService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validOneUserWithOneOrderAndThreeProducts(), response)
    }

    @Test
    fun `must return UserTxtFileResponseDTO with three users with order and product`() {
        // given
        val dataFile = File("src/test/resources/data/valid-test-three-users-with-order-and-product.txt")
        val file: MultipartFile = MockMultipartFile(
            "test.txt",
            "valid-test-three-users-with-order-and-product.txt",
            "text/plain",
            dataFile.inputStream()
        )

        // when
        val response = userService.processTxtFile(file)

        // then
        assertEquals(DataFixtureFactory.ValidDataResponses.validThreeUsersWithOrderAndProduct(), response)
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
            userService.processTxtFile(file)
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
            userService.processTxtFile(file)
            fail("it should return an invalid number of characters error")
        } catch (e: Exception) {
            // then
            val expectedErrorMessage = "M=processTxtFile - Error: invalid line length (line: " +
                    "0000000088                             Terra Daniel DDS00000008360000000003     1899)"
            assertEquals(expectedErrorMessage, e.message)
        }
    }
}