package vitorsb.project.logidataprocess.dto.order

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class OrderDTO(
    @field:NotNull
    val externalId: Long,

    @field:NotNull
    val userId: Long,

    @field:NotNull
    val purchaseDate: LocalDate
)
