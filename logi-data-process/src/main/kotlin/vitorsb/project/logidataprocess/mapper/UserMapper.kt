package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.utils.DateUtils

@Component
class UserMapper {
    private val orderMapper: OrderMapper = OrderMapper()
    fun toProcessTxtLineDTO(line: String): ProcessTxtLineDTO {
        return ProcessTxtLineDTO(
            userId = line.substring(0, 10).toLong(),
            userName = line.substring(10, 55).trimStart(),
            orderId = line.substring(55, 65).toLong(),
            productId = line.substring(65, 75).toLong(),
            productValue = line.substring(75, 87).toDouble(),
            purchaseDate = DateUtils().formatStringToLocalDate(line.substring(87, 95))
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