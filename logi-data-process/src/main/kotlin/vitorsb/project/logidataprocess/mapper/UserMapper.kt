package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.order.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.dto.user.UserDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.User

@Component
class UserMapper {
    private val orderMapper: OrderMapper = OrderMapper()

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