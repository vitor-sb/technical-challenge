package vitorsb.project.logidataprocess.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateUtils {
    fun formatStringToLocalDate(unformattedDate: String): LocalDate {
        val year = unformattedDate.substring(0, 4)
        val month = unformattedDate.substring(4, 6)
        val day = unformattedDate.substring(6, 8)

        val formattedDate = "$year-$month-$day"

        return try {
            LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE)
        } catch (e: Exception) {
            throw Exception("Invalid date format: $formattedDate")
        }
    }
}