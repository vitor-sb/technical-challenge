package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import vitorsb.project.logidataprocess.dto.product.ProductDTO
import vitorsb.project.logidataprocess.dto.product.ProductTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.Product
import vitorsb.project.logidataprocess.mapper.ProductMapper
import vitorsb.project.logidataprocess.repository.ProductRepository

@Service
class ProductService(
    private val repository: ProductRepository,
) {
    private val mapper: ProductMapper = ProductMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createProduct(product: ProductTxtFileResponseDTO): Product {
        logger.info("M=createOrder - Creating order with product_id: ${product.product_id}")

        val productDTO: ProductDTO = mapper.toDto(product)

        return repository.save(mapper.toEntity(productDTO))
    }
}