package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.entity.Product
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.mapper.OrderMapper.toOrder
import java.io.BufferedReader
import java.io.InputStreamReader
import vitorsb.project.logidataprocess.mapper.UserMapper.toProcessTxtLineDTO
import vitorsb.project.logidataprocess.mapper.UserMapper.toUser

@Service
class UserService{

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun processTxtFile(file: MultipartFile) {
        logger.info("M=processTxtFile - Processing txt file")

        val lines = try {
             BufferedReader(InputStreamReader(file.inputStream)).readLines()
        } catch (e: Exception) {
            logger.error("M=processTxtFile - Error: ${e.message}")
            throw Exception("M=processTxtFile - Error: error reading file")
        }

        val users: MutableList<User> = mutableListOf()

        lines.forEach { line ->
            if(line.trim().isNotEmpty()){

                if(line.length != 95)
                    throw Exception("M=processTxtFile - Error: invalid line length (line: $line)")

                val lineDTO = line.toProcessTxtLineDTO()

                val registeredUser = users.find { user -> user.id == lineDTO.userId }
                registeredUser?.let {

                    val registeredOrder = registeredUser.orders.find { order -> order.id == lineDTO.orderId }
                    registeredOrder?.let {

                        registeredOrder.products.add(
                            Product(
                                id = lineDTO.productId,
                                value = lineDTO.productValue
                            )
                        )

                    } ?: registeredUser.orders.add(lineDTO.toOrder())

                } ?: users.add(lineDTO.toUser())

            }
        }
        logger.info("M=processTxtFile - Users: ${users.size}")
    }
}