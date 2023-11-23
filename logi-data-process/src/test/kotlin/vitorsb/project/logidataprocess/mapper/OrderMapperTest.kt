package vitorsb.project.logidataprocess.mapper

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.fixtures.DataFixtureFactory

class OrderMapperTest {
    private val orderMapper: OrderMapper = OrderMapper()

    @Test
    fun `should return invalid id number format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDatas.oneUserWithInvalidId
        // when
        try{
            val lineDto = orderMapper.toProcessTxtLineDTO(invalidParameter)
            fail("it should return an invalid id format error, id: ${lineDto.userId}")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("For input string: \"00000oito8\"", e.message)
        }
        // then
    }

    @Test
    fun `should return invalid product value number format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDatas.oneUserWithInvalidProductValue

        // when
        try {
            val lineDto = orderMapper.toProcessTxtLineDTO(invalidParameter)
            fail("it should return an invalid product value format error, value: ${lineDto.productValue}")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals("For input string: \"18 9.02\"", e.message)
        }
        // then
    }
}