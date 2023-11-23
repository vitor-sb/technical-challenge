package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import vitorsb.project.logidataprocess.dto.product.ProductDTO
import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.entity.Product
import vitorsb.project.logidataprocess.exception.NotFoundException
import vitorsb.project.logidataprocess.mapper.ProductMapper
import vitorsb.project.logidataprocess.repository.ProductRepository

@Service
class ProductService {
    @Autowired
    private lateinit var repository: ProductRepository

    private val mapper: ProductMapper = ProductMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createProduct(product: ProductResponseDTO): Product {
        logger.debug("M=createOrder - Creating order with product_id: ${product.product_id}")

        val productDTO: ProductDTO = mapper.toDto(product)

        return repository.save(mapper.toEntity(productDTO))
    }

    fun findByOrderId(orderId: Long): MutableList<Product> {
        logger.debug("M=findByOrderId - Finding products by orderId: $orderId")

        val products = repository.findByOrderId(orderId).ifEmpty {
            throw NotFoundException("M=findByOrderId, orderId=$orderId - Products not found!")
        }

        return products
    }
}