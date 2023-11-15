package vitorsb.project.logidatatransform.dto.exception

import java.time.LocalDateTime

data class ErrorDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String? = null,
    val path: String
)
