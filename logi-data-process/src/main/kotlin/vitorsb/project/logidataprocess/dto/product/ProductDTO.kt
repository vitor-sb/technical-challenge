package vitorsb.project.logidataprocess.dto.product

import javax.validation.constraints.NotNull

data class ProductDTO(
    @field:NotNull
    val externalId: Long,
    @field:NotNull
    val value: Double
)
