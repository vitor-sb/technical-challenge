package vitorsb.project.logidataprocess.mapper

import org.springframework.stereotype.Component
import vitorsb.project.logidataprocess.dto.ProcessTxtLineDTO
import vitorsb.project.logidataprocess.entity.User
import vitorsb.project.logidataprocess.mapper.OrderMapper.toOrder
import vitorsb.project.logidataprocess.utils.DateFormat

@Component
object UserMapper {
    fun String.toProcessTxtLineDTO(): ProcessTxtLineDTO {
        val userId = this.substring(0, 10).toLong()
        val userName = this.substring(10, 55).trimStart()
        val orderId = this.substring(55, 65).toLong()
        val productId = this.substring(65, 75).toLong()
        val productValue = this.substring(75, 87).toDouble()
        val purchaseDate = DateFormat().formatStringToLocalDate(this.substring(87, 95))

        return ProcessTxtLineDTO(
            userId = userId,
            userName = userName,
            orderId = orderId,
            productId = productId,
            productValue = productValue,
            purchaseDate = purchaseDate
        )
    }

    fun ProcessTxtLineDTO.toUser(): User {
        return User(
            id = this.userId,
            name = this.userName,
            orders = mutableListOf(this.toOrder())
        )
    }
}