package vitorsb.project.logidataprocess.dto.user

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserDTO(
    @field:NotNull
    val id: Long,

    @field:NotEmpty
    @field:Size(min = 2, max = 45)
    val name: String
)