package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.mapper.ProductMapper.toProduct

@Component
object OrderMapper {
    fun ProcessTxtLineDTO.toOrder(): Order {
        return Order(
            id = this.orderId,
            purchaseDate = this.purchaseDate,
            products = mutableListOf(this.toProduct())
        )
    }
}