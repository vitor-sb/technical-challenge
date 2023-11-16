package vitorsb.project.logidataprocess.dto

import java.time.LocalDate

data class ProcessTxtLineDTO(
    val userId: Long,
    val userName: String,
    val orderId: Long,
    val productId: Long,
    val productValue: Double,
    val purchaseDate: LocalDate
)
//data class UserWithOrdersResponseDTO(
//    val user_id: Long,
//    val name: String,
//    val orders: List<OrderDTO>
//)
//data class ProcessTxtFileResponseDTO(
//    val users: List<UserDTO>
//)