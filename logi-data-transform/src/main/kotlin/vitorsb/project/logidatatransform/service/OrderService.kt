package vitorsb.project.logidatatransform.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class OrderService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun processTxtFile(file: MultipartFile) {
        logger.info("M=processTxtFile - Processing txt file")

        val lines = try {
             BufferedReader(InputStreamReader(file.inputStream)).readLines()
        } catch (e: Exception) {
            throw Exception("M=processTxtFile - Error processing file: ${e.message}")
        }

        lines.forEach {
            logger.info("M=processTxtFile - Line: $it")
        }
    }
}