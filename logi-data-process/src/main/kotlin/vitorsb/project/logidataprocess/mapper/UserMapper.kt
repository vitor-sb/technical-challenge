package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.utils.DateUtils

@Component
class UserMapper(
    val orderMapper: OrderMapper
) {
    fun toProcessTxtLineDTO(line: String): ProcessTxtLineDTO {
        val userId = line.substring(0, 10).toLong()
        val userName = line.substring(10, 55).trimStart()
        val orderId = line.substring(55, 65).toLong()
        val productId = line.substring(65, 75).toLong()
        val productValue = line.substring(75, 87).toDouble()
        val purchaseDate = DateUtils().formatStringToLocalDate(line.substring(87, 95))

        return ProcessTxtLineDTO(
            userId = userId,
            userName = userName,
            orderId = orderId,
            productId = productId,
            productValue = productValue,
            purchaseDate = purchaseDate
        )
    }

    fun toUserTxtFileResponseDTO(lineDTO: ProcessTxtLineDTO): UserTxtFileResponseDTO {
        return UserTxtFileResponseDTO(
            user_id = lineDTO.userId,
            name = lineDTO.userName,
            orders = mutableListOf(
                orderMapper.toOrderTxtFileResponseDTO(lineDTO)
            )
        )
    }
}