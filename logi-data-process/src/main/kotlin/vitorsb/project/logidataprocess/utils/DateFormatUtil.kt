package vitorsb.project.logidataprocess.utils

import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateUtils {
    private val logger = LoggerFactory.getLogger(this::class.java)
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

    fun validateAndFormatDate(stringDate: String): LocalDate {

        val formatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ISO_DATE
        )

        var formattedDate: LocalDate?

        for (formatter in formatters) {
            try {
                formattedDate =  LocalDate.parse(stringDate, formatter)
            } catch (e: DateTimeParseException){
                logger.debug("M=validateAndFormatDate - " +
                        "Trying to parse date: $stringDate with formatter: $formatter")
            }
        }

        if(formattedDate == null)
            throw Exception("Invalid date format: $stringDate")


        return formatStringToLocalDate(date)
    }
}