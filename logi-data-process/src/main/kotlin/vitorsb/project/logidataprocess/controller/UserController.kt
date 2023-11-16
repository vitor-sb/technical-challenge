package vitorsb.project.logidataprocess.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.exception.InvalidFileTypeException
import vitorsb.project.logidataprocess.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {

    @PostMapping("/processTxtFile")
    @ResponseBody
    fun processTxtFile(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, Any>> {
        if (!file.contentType.equals("text/plain"))
            throw InvalidFileTypeException("The file must be text type")

        service.processTxtFile(file)

        return ResponseEntity.ok(mapOf("status" to "success", "message" to "File processed successfully"))
    }
}