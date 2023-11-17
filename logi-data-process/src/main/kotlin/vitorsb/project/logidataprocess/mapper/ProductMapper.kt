package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.product.ProductTxtFileResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO

@Component
class ProductMapper {
    fun toProductTxtFileResponseDTO(productTxtLine: ProcessTxtLineDTO): ProductTxtFileResponseDTO {
        return ProductTxtFileResponseDTO(
            product_id = productTxtLine.productId,
            value = productTxtLine.productValue
        )
    }
}