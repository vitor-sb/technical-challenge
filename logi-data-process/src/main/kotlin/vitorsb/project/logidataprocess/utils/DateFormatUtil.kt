package vitorsb.project.logidataprocess.utils

import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatUtil {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun formatDate(stringDate: String): LocalDate {
        if(stringDate.isEmpty())
            throw IllegalArgumentException("M=validateAndFormatDate - Date cannot be empty")

        val formatters = listOf(
            DateTimeFormatter.ofPattern("yyyyMMdd"),
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