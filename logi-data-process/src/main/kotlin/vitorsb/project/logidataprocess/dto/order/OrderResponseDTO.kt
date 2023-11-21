package vitorsb.project.logidataprocess.dto.order

import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import java.time.LocalDate

data class OrderResponseDTO(
    val order_id: Long,
    var total: Double = 0.00,
    val date: LocalDate,
    val products: MutableList<ProductResponseDTO> = mutableListOf()
)
