package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.order.OrderDTO
import vitorsb.project.logidataprocess.dto.order.OrderProductRelationDTO
import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.dto.order.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.OrderProductRelation
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.utils.CalcUtil
import vitorsb.project.logidataprocess.utils.DateFormatUtil

@Component
class OrderMapper {
    private val productMapper: ProductMapper = ProductMapper()

    fun toProcessTxtLineDTO(line: String): ProcessTxtLineDTO {
        return ProcessTxtLineDTO(
            userId = line.substring(0, 10).toLong(),
            userName = line.substring(10, 55).trimStart(),
            orderId = line.substring(55, 65).toLong(),
            productId = line.substring(65, 75).toLong(),
            productValue = line.substring(75, 87).toDouble(),
            purchaseDate = DateFormatUtil.formatDate(line.substring(87, 95))
        )
    }

    fun toOrderTxtFileResponseDTO(lineDTO: ProcessTxtLineDTO): OrderResponseDTO {
        return OrderResponseDTO(
            order_id = lineDTO.orderId,
            total = lineDTO.productValue,
            date = lineDTO.purchaseDate,
            products = mutableListOf(
                productMapper.toProductTxtFileResponseDTO(lineDTO)
            )
        )
    }

    fun toEntity(dto: OrderDTO, user: User): Order {
        return Order(
            externalId = dto.externalId,
            purchaseDate = dto.purchaseDate,
            userId = dto.userId,
            user = user
        )
    }

    fun responseDtoToDto(it: OrderResponseDTO, userId: Long): OrderDTO {
        return OrderDTO(
            externalId = it.order_id,
            userId = userId,
            purchaseDate = it.date
        )
    }

    fun orderProductRelationDTO(orderId: Long, productId: Long): OrderProductRelationDTO {
        return OrderProductRelationDTO(
            orderId = orderId,
            productId = productId
        )
    }

    fun toOrderProductRelationEntity(dto: OrderProductRelationDTO): OrderProductRelation {
        return OrderProductRelation(
            orderId = dto.orderId,
            productId = dto.productId
        )
    }

    fun toDto(order: Order): OrderResponseDTO {
        val productsResponseDTO: MutableList<ProductResponseDTO> = order.products.map {
            productMapper.toProductResponseDTO(it)
        }.toMutableList()

        val formattedTotal = CalcUtil().roundsValue(order.products.sumOf { it.value })
        return OrderResponseDTO(
            order_id = order.externalId,
            total = formattedTotal,
            date = order.purchaseDate,
            products = productsResponseDTO
        )

    }

    fun toDto(orders: List<Order>): MutableList<OrderResponseDTO> {
        return orders.map {
            toDto(it)
        }.toMutableList()
    }
}