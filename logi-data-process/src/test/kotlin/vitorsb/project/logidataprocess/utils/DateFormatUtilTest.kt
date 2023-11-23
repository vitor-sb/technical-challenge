package vitorsb.project.logidataprocess.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import vitorsb.project.logidataprocess.fixtures.DataFixtureFactory

class DateFormatUtilTest {
    @Test
    fun `should parse valid date string in different formats`() {
        // given
        val validDateStrings = listOf(
            "20211231",
            "2021-12-31",
            "2021/12/31",
            "2021.12.31",
            "31-12-2021",
            "31/12/2021",
            "2021-12-31+10:15:30"
        )

        // when
        for (dateString in validDateStrings) {
            val parsedDate = DateFormatUtil.formatDate(dateString)

            // then
            Assertions.assertNotNull(parsedDate)
        }
    }

    @Test
    fun `should throw exception for invalid date string`() {
        // given
        val invalidDateStrings = listOf(
            "2021-13-31",
            "2021/12/31",
            "2021.12.31",
            "31-12-2021",
            "31/12/2021",
            "invalidDateString"
        )

        // when
        for (dateString in invalidDateStrings) {
            try {
                DateFormatUtil.formatDate(dateString)
            }catch (exception: IllegalArgumentException) {
                // then
                assertTrue(exception.message?.contains("Invalid date format") ?: false)
            }

        }
    }

    @Test
    fun `should throw exception for empty date string`() {
        // given
        val emptyDateString = ""

        // when
        val exception = assertThrows<IllegalArgumentException> {
            DateFormatUtil.formatDate(emptyDateString)
        }

        // then
        assertEquals("M=validateAndFormatDate - Date cannot be empty", exception.message)
    }
}