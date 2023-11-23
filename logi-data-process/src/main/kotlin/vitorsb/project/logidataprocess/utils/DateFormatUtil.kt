package vitorsb.project.logidataprocess.utils

import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatUtil {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun formatStringToLocalDate(unformattedDate: String): LocalDate {
        val year = unformattedDate.substring(0, 4)
        val month = unformattedDate.substring(4, 6)
        val day = unformattedDate.substring(6, 8)

        val formattedDate = "$year-$month-$day"

        return try {
            LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE)
        } catch (e: Exception) {
            throw Exception("M=validateAndFormatDate - Invalid date format: $formattedDate")
        }
    }

    fun formatDate(stringDate: String): LocalDate {
        if(stringDate.isEmpty())
            throw IllegalArgumentException("M=validateAndFormatDate - Date cannot be empty")

        val formatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ISO_DATE
        )

        for (formatter in formatters) {
            try {
                return LocalDate.parse(stringDate, formatter)
            } catch (e: DateTimeParseException){
                logger.debug("M=formatDate - " +
                        "Trying to parse date: $stringDate with formatter: $formatter")
            }
        }

        throw IllegalArgumentException("M=formatDate - Invalid date format: $stringDate")
    }
}