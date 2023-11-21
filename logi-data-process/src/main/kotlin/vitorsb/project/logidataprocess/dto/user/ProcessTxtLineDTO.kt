package vitorsb.project.logidataprocess.dto.user

import java.time.LocalDate

data class ProcessTxtLineDTO(
    val userId: Long,
    val userName: String,
    val orderId: Long,
    val productId: Long,
    val productValue: Double,
    val purchaseDate: LocalDate
)
