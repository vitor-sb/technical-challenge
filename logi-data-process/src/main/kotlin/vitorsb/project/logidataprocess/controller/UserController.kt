package vitorsb.project.logidataprocess.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.exception.InvalidFileTypeException
import vitorsb.project.logidataprocess.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController @Autowired constructor(
    private val service: UserService
) {

    @PostMapping("/processTxtFile")
    @ResponseBody
    fun processTxtFile(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("toSave", required = false) toSave: Boolean? = false
    ): ResponseEntity<MutableList<UserTxtFileResponseDTO>> {
        if (!file.contentType.equals("text/plain"))
            throw InvalidFileTypeException("M=processTxtFile - File The file must be text type")

        val response = service.processTxtFile(file, toSave)

        return ResponseEntity.ok(response)
    }
}