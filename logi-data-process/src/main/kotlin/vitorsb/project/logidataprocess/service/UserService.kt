package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.mapper.OrderMapper
import vitorsb.project.logidataprocess.mapper.ProductMapper
import vitorsb.project.logidataprocess.mapper.UserMapper
import vitorsb.project.logidataprocess.repository.UserRepository
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class UserService(
    private val repository: UserRepository,
    private val orderService: OrderService,
    private val productService: ProductService
) {
    private val mapper: UserMapper = UserMapper()
    private val orderMapper: OrderMapper = OrderMapper()
    private val productMapper: ProductMapper = ProductMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun processTxtFile(
        file: MultipartFile,
        toSave: Boolean? = false)
    : MutableList<UserTxtFileResponseDTO> {
        logger.info("M=processTxtFile - Processing txt file")

        val lines = try {
            val fileLines = BufferedReader(InputStreamReader(file.inputStream)).readLines()

            if (fileLines.isEmpty())
                throw Exception("M=processTxtFile - Error: empty file")

            fileLines
        } catch (e: Exception) {
            logger.error("M=processTxtFile - Error: ${e.message}")
            throw Exception("M=processTxtFile - Error: error reading file")
        }

        val processResponse: MutableList<UserTxtFileResponseDTO> = mutableListOf()

        lines.forEach { line ->
            if(line.trim().isNotEmpty()){
                if(line.length != 95)
                    throw Exception("M=processTxtFile - Error: invalid line length (line: $line)")

                val lineDTO = mapper.toProcessTxtLineDTO(line)
                val foundUser = processResponse.find { user -> user.user_id == lineDTO.userId }

                if(foundUser !== null) {
                    createOrUpdateOrder(foundUser, lineDTO)
                } else {
                    logger.debug("M=processTxtFile - Creating new user: ${lineDTO.userId}")
                    processResponse.add(mapper.toUserTxtFileResponseDTO(lineDTO))
                }
            }
        }

        if(toSave == true)
            createUsers(processResponse)

        return processResponse
    }

    private fun createOrUpdateOrder(foundUser: UserTxtFileResponseDTO, lineDTO: ProcessTxtLineDTO) {
        val foundOrder = foundUser.orders.find { order -> order.order_id == lineDTO.orderId }

        if(foundOrder !== null) {
            logger.debug("M=createOrUpdateOrder - Adding product to order: ${lineDTO.orderId}")

            foundOrder.products.add(
                productMapper.toProductTxtFileResponseDTO(lineDTO)
            )
            foundOrder.total += lineDTO.productValue

        } else {
            logger.debug("M=createOrUpdateOrder - Creating new order for user: ${foundUser.user_id}")
            foundUser.orders.add(orderMapper.toOrderTxtFileResponseDTO(lineDTO))
        }
    }

    @Transactional
    fun createUsers(users: MutableList<UserTxtFileResponseDTO>) {
        logger.info("M=saveUsers - Saving ${users.size} users")

        users.forEach { user ->
            val persistedUser = createUser(user)
            user.orders.forEach { order ->
                val persistedOrder = orderService.createOrder(order, persistedUser)
                order.products.forEach { product ->
                    val persistedProduct = productService.createProduct(product)
                    orderService.relateOrderProduct(persistedOrder.id!!, persistedProduct.id!!)
                }
            }
        }
    }

    @Transactional
    fun createUser(user: UserTxtFileResponseDTO): User {
        logger.debug("M=saveUser - Saving user with id:${user.user_id}")

        val userDTO: UserDTO = mapper.toDto(user)

        return repository.save(mapper.toEntity(userDTO))
    }
}