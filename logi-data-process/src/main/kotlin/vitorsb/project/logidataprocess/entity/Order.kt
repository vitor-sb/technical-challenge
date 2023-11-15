package vitorsb.project.logidataprocess.entity

import java.time.LocalDateTime
import javax.persistence.Id

data class Order(
    @Id
    var id: Long? = null,
    val purchaseDate: LocalDateTime,
    val user: User,
    val products: List<Product>
){
    fun totalValue(): Double {
        return products.sumOf { it.value }
    }
}
