package controller

import fixtures.DataFixtureFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.controller.UserController
import java.io.File

class UserControllerTest {
    private val userController = UserController()

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
        val response = userController.processTxtFile(file)

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
            userController.processTxtFile(file)
            fail("it should return an invalid file type")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("M=processTxtFile - File The file must be text type", e.message)
        }
    }
}