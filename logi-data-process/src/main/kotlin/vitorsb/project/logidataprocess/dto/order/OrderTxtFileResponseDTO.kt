package vitorsb.project.logidataprocess.dto.order

import vitorsb.project.logidataprocess.dto.product.ProductTxtFileResponseDTO
import java.time.LocalDate

data class OrderTxtFileResponseDTO(
    val order_id: Long,
    var total: Double = 0.00,
    val date: LocalDate,
    val products: MutableList<ProductTxtFileResponseDTO> = mutableListOf()
)
