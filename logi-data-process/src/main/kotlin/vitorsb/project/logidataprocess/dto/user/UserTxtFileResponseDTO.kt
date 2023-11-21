package vitorsb.project.logidataprocess.dto.user

import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO

data class UserTxtFileResponseDTO(
    val user_id: Long,
    val name: String,
    val orders: MutableList<OrderResponseDTO> = mutableListOf()
)
