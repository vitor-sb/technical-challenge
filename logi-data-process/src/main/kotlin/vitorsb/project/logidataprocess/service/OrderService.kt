package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import vitorsb.project.logidataprocess.dto.order.OrderDTO
import vitorsb.project.logidataprocess.dto.order.OrderTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.OrderProductRelation
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.mapper.OrderMapper
import vitorsb.project.logidataprocess.repository.OrderProductRelationRepository
import vitorsb.project.logidataprocess.repository.OrderRepository

@Service
class OrderService(
    private val repository: OrderRepository,
    private val orderProductRelationRepository: OrderProductRelationRepository
) {
    private val mapper: OrderMapper = OrderMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createOrder(order: OrderTxtFileResponseDTO, user: User): Order {
        logger.info("M=createOrder - Creating order with order_id: ${order.order_id}")

        val orderDTO: OrderDTO = mapper.toDto(order, user.id)

        return repository.save(mapper.toEntity(orderDTO, user))
    }

    @Transactional
    fun relateOrderProduct(orderId: Long, productId: Long): OrderProductRelation {
        logger.info("M=relateOrderProduct - Relating order with product")

        val orderProductRelationDTO = mapper.orderProductRelationDTO(orderId, productId)

        return orderProductRelationRepository.save(mapper.toOrderProductRelationEntity(orderProductRelationDTO))
    }
}