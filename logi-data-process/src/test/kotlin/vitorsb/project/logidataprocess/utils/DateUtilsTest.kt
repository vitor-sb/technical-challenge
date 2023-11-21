package utils

import fixtures.DataFixtureFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.utils.DateUtils

class DateUtilsTest {

    @Test
    fun `Should return invalid purchase date format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDataParameters.invalidDateValue

        try {
            // when
            val date = DateUtils().formatStringToLocalDate(invalidParameter)
            fail("it should return an invalid date format error, date: $date")
        } catch (e: Exception) {
            // then
            Assertions.assertEquals(
                "Invalid date format: 2021-13-31",
                e.message
            )
        }
    }
}