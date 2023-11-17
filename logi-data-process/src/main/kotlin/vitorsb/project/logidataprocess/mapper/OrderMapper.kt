package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.order.OrderTxtFileResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO

@Component
class OrderMapper(
    val productMapper: ProductMapper
) {
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
}