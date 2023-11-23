package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.dto.order.OrderDTO
import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.OrderProductRelation
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.exception.NotFoundException
import vitorsb.project.logidataprocess.mapper.OrderMapper
import vitorsb.project.logidataprocess.mapper.ProductMapper
import vitorsb.project.logidataprocess.mapper.UserMapper
import vitorsb.project.logidataprocess.repository.OrderProductRelationRepository
import vitorsb.project.logidataprocess.repository.OrderRepository
import vitorsb.project.logidataprocess.utils.DateFormatUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate

@Service
class OrderService {

    @Autowired
    private lateinit var repository: OrderRepository

    @Autowired
    private lateinit var orderProductRelationRepository: OrderProductRelationRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var productService: ProductService

    private val mapper: OrderMapper = OrderMapper()
    private val userMapper: UserMapper = UserMapper()
    private val productMapper: ProductMapper = ProductMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createOrder(order: OrderResponseDTO, user: User): Order {
        logger.debug("M=createOrder - Creating order with order_id: ${order.order_id}")

        val orderDTO: OrderDTO = mapper.responseDtoToDto(order, user.id)

        return repository.save(mapper.toEntity(orderDTO, user))
    }

    fun findByUserIdAndExternalId(userId: Long, orderId: Long): OrderResponseDTO {
        logger.debug("M=findById - Finding order by orderId=$orderId, userId: $userId")

        userService.findById(userId)

        val order: Order = repository.findByExternalIdAndUserId(userId = userId, externalId = orderId)
            ?: throw NotFoundException("M=findById, orderId=$orderId, userId: $userId - Order not found!")

        order.products = productService.findByOrderId(order.id!!)

        return mapper.toDto(order)
    }

    fun findOrdersByUserId(
        userId: Long,
    ): MutableList<OrderResponseDTO> {
        logger.debug("M=findByUserId - Finding order by userId: $userId")

        userService.findById(userId)

        val orders: List<Order> = repository.findByUserId(userId).ifEmpty {
            throw NotFoundException("M=findByUserId - Order not found!")
        }

        orders.forEach {
            it.products = productService.findByOrderId(it.id!!)
        }

        return mapper.toDto(orders)
    }

    fun findByUserIdAndPurchaseDateBetween(
        userId: Long,
        startDate: String?,
        endDate: String?
    ): MutableList<OrderResponseDTO> {
        logger.debug("M=findByUserIdAndPurchaseDateBetween - " +
                "Finding order by userId: $userId, initialDate: $startDate, finalDate: $endDate")

        val orders: List<Order> = when {
             startDate !== null && endDate !== null -> {
                val initialDate = DateFormatUtil.formatDate(startDate)
                val finalDate = DateFormatUtil.formatDate(endDate)
                validateDates(initialDate, finalDate)

                repository.findByUserIdAndPurchaseDateBetween(userId, initialDate, finalDate).ifEmpty {
                    throw NotFoundException("M=findByUserIdAndPurchaseDateBetween - Orders not found within the period!")
                }
            }
            startDate !== null -> {
                val initialDate = DateFormatUtil.formatDate(startDate)
                validateDates(initialDate)

                repository.findByUserIdAndPurchaseDateBetween(userId, initialDate, LocalDate.now()).ifEmpty {
                    throw NotFoundException("M=findByUserIdAndPurchaseDateBetween - Orders not found within the period!")
                }
            }
            endDate !== null -> {
                val finalDate = DateFormatUtil.formatDate(endDate)
                validateDates(finalDate)

                repository.findByUserIdAndPurchaseDateUntil(userId, finalDate).ifEmpty {
                    throw NotFoundException("M=findByUserIdAndPurchaseDateBetween - Orders not found within the period!")
                }
            }
            else -> {
                throw IllegalArgumentException(
                    "M=findByUserIdAndPurchaseDateBetween - " +
                            "Initial date and final date cannot be null at the same time!"
                )
            }
        }

        orders.forEach {
            it.products = productService.findByOrderId(it.id!!)
        }

        return mapper.toDto(orders)
    }

    private fun validateDates(initialDate: LocalDate, finalDate: LocalDate? = null) {
        if(initialDate.isAfter(LocalDate.now()))
            throw IllegalArgumentException(
                "M=findByUserIdAndPurchaseDateBetween - Initial date must be before today!"
            )

        if (finalDate !== null) {
            if (initialDate.isAfter(finalDate))
                throw IllegalArgumentException(
                    "M=findByUserIdAndPurchaseDateBetween - Initial date must be before final date!"
                )

            if(finalDate.isAfter(LocalDate.now()))
                throw IllegalArgumentException(
                    "M=findByUserIdAndPurchaseDateBetween - Final date must be before today!"
                )
        }
    }

    @Transactional
    fun relateOrderProduct(orderId: Long, productId: Long): OrderProductRelation {
        logger.debug("M=relateOrderProduct - Relating order with product")

        val orderProductRelationDTO = mapper.orderProductRelationDTO(orderId, productId)

        return orderProductRelationRepository.save(
            mapper.toOrderProductRelationEntity(orderProductRelationDTO)
        )
    }

    @Transactional
    fun processTxtFile(
        file: MultipartFile,
        toSave: Boolean? = false
    ) : MutableList<UserTxtFileResponseDTO> {
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

                val lineDTO = userMapper.toProcessTxtLineDTO(line)
                val foundUser = processResponse.find { user -> user.user_id == lineDTO.userId }

                if(foundUser !== null) {
                    createOrUpdateOrder(foundUser, lineDTO)
                } else {
                    logger.debug("M=processTxtFile - Creating new user: ${lineDTO.userId}")
                    processResponse.add(userMapper.toUserTxtFileResponseDTO(lineDTO))
                }
            }
        }

        if(toSave == true)
            persistUsersAndOrdersAndProducts(processResponse)

        return processResponse
    }

    private fun createOrUpdateOrder(
        foundUser: UserTxtFileResponseDTO,
        lineDTO: ProcessTxtLineDTO
    ) {
        val foundOrder = foundUser.orders.find { order -> order.order_id == lineDTO.orderId }

        if(foundOrder !== null) {
            logger.debug("M=createOrUpdateOrder - Adding product to order: ${lineDTO.orderId}")

            foundOrder.products.add(
                productMapper.toProductTxtFileResponseDTO(lineDTO)
            )
            foundOrder.total += lineDTO.productValue

        } else {
            logger.debug("M=createOrUpdateOrder - Creating new order for user: ${foundUser.user_id}")
            foundUser.orders.add(mapper.toOrderTxtFileResponseDTO(lineDTO))
        }
    }

    private fun persistUsersAndOrdersAndProducts(
        users: MutableList<UserTxtFileResponseDTO>
    ) {
        logger.debug("M=saveUsers - Saving ${users.size} users")

        users.forEach { user ->
            val persistedUser = userService.createUser(user)
            user.orders.forEach { order ->
                val persistedOrder = createOrder(order, persistedUser)
                order.products.forEach { product ->
                    val persistedProduct = productService.createProduct(product)
                    relateOrderProduct(persistedOrder.id!!, persistedProduct.id!!)
                }
            }
        }
    }
}