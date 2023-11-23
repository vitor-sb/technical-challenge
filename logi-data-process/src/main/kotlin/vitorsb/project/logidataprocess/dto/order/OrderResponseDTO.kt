package vitorsb.project.logidataprocess.dto.order

import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.utils.CalcUtil
import java.time.LocalDate

data class OrderResponseDTO(
    val order_id: Long,
    var total: Double = 0.00,
    val date: LocalDate,
    val products: MutableList<ProductResponseDTO> = mutableListOf()
){
    fun addProduct(product: ProductResponseDTO){
        this.products.add(product)
        sumTotal()
    }
    fun sumTotal(){
        val sumValues: Double = products.sumOf { it.value }
        this.total = CalcUtil().roundsValue(sumValues)
    }
}
