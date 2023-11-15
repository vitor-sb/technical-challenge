package vitorsb.project.logidatatransform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class LogiDataTransformApplication

fun main(args: Array<String>) {
	TimeZone.setDefault(TimeZone.getTimeZone("utc"))
	runApplication<LogiDataTransformApplication>(*args)
}
