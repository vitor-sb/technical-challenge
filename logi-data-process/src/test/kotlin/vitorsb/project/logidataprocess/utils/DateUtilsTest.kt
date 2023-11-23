package vitorsb.project.logidataprocess.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.fixtures.DataFixtureFactory

class DateUtilsTest {

    @Test
    fun `Should return invalid purchase date format error`() {
        // given
        val invalidParameter = DataFixtureFactory.InvalidDatas.invalidDateValue

        try {
            // when
            val date = DateFormatUtil.formatStringToLocalDate(invalidParameter)
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