package vitorsb.project.logidataprocess.dto.order

import javax.validation.constraints.NotNull

data class OrderProductRelationDTO(
    @field:NotNull
    val orderId: Long,
    @field:NotNull
    val productId: Long
)
