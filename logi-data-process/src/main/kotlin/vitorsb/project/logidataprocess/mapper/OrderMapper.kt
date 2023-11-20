package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.order.OrderDTO
import vitorsb.project.logidataprocess.dto.order.OrderProductRelationDTO
import vitorsb.project.logidataprocess.dto.order.OrderTxtFileResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.OrderProductRelation
import vitorsb.project.logidataprocess.entity.Product
import vitorsb.project.logidataprocess.entity.User

@Component
class OrderMapper {
    private val productMapper: ProductMapper = ProductMapper()
    fun toOrderTxtFileResponseDTO(lineDTO: ProcessTxtLineDTO): OrderTxtFileResponseDTO {
        return OrderTxtFileResponseDTO(
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
            user = user
        )
    }

    fun toDto(it: OrderTxtFileResponseDTO, userId: Long): OrderDTO {
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
            order_id = dto.orderId,
            product_id = dto.productId
        )
    }
}