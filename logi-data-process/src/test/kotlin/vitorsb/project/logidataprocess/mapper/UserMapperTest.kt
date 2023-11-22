package vitorsb.project.logidataprocess.mapper

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.fixtures.DataFixtureFactory

class UserMapperTest {
    private val userMapper: UserMapper = UserMapper()

    @Test
    fun `Should return invalid id number format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDataParameters.oneUserWithInvalidId
        // when
        try{
            val lineDto = userMapper.toProcessTxtLineDTO(invalidParameter)
            fail("it should return an invalid id format error, id: ${lineDto.userId}")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("For input string: \"00000oito8\"", e.message)
        }
        // then
    }

    @Test
    fun `Should return invalid purchase date format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDataParameters.oneUserWithInvalidPurchaseDate

        // when
        try {
            val lineDto = userMapper.toProcessTxtLineDTO(invalidParameter)
            fail("it should return an invalid date format error, date: ${lineDto.purchaseDate}")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals(
                "Invalid date format: 2021-13-31",
                e.message
            )
        }
        // then
    }

    @Test
    fun `Should return invalid product value number format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDataParameters.oneUserWithInvalidProductValue

        // when
        try {
            val lineDto = userMapper.toProcessTxtLineDTO(invalidParameter)
            fail("it should return an invalid product value format error, value: ${lineDto.productValue}")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("For input string: \"18 9.02\"", e.message)
        }
        // then
    }
}