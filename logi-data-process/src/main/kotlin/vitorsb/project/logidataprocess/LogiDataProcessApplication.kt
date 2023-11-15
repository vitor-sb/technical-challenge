package vitorsb.project.logidataprocess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class LogiDataProcessApplication

fun main(args: Array<String>) {
	TimeZone.setDefault(TimeZone.getTimeZone("utc"))
	runApplication<LogiDataProcessApplication>(*args)
}
