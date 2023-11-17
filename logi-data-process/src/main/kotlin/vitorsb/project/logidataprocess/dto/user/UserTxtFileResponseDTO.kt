package vitorsb.project.logidataprocess.dto.user

import vitorsb.project.logidataprocess.dto.order.OrderTxtFileResponseDTO

data class UserTxtFileResponseDTO(
    val user_id: Long,
    val name: String,
    val orders: MutableList<OrderTxtFileResponseDTO> = mutableListOf()
)
