package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.product.ProductDTO
import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.Product

@Component
class ProductMapper {
    fun toProductTxtFileResponseDTO(it: ProcessTxtLineDTO): ProductResponseDTO {
        return ProductResponseDTO(
            product_id = it.productId,
            value = it.productValue
        )
    }

    fun toEntity(it: ProductDTO): Product {
        return Product(
            externalId = it.externalId,
            value = it.value
        )
    }

    fun toDto(order: ProductResponseDTO): ProductDTO {
        return ProductDTO(
            externalId = order.product_id,
            value = order.value
        )
    }

    fun toProductResponseDTO(it: Product): ProductResponseDTO {
        return ProductResponseDTO(
            product_id = it.externalId,
            value = it.value
        )
    }
}