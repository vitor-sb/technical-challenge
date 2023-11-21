package vitorsb.project.logidataprocess.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import vitorsb.project.logidataprocess.entity.Product

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN OrderProductRelation opr ON p.id = opr.productId WHERE opr.orderId = :orderId")
    fun findByOrderId(@Param("orderId") orderId: Long): MutableList<Product>
}