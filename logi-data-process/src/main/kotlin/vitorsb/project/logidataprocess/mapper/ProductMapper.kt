package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.Product

@Component
object ProductMapper {
    fun ProcessTxtLineDTO.toProduct(): Product {
        return Product(
            id = productId,
            value = productValue
        )

    }
}