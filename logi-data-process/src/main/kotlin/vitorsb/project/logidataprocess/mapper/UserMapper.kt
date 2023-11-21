package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.user.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.User
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

    fun toDto(user: UserTxtFileResponseDTO): UserDTO {
        return UserDTO(
            id = user.user_id,
            name = user.name,
        )
    }

    fun toEntity(userDTO: UserDTO): User {
        return User(
            id = userDTO.id,
            name = userDTO.name,
        )
    }
}