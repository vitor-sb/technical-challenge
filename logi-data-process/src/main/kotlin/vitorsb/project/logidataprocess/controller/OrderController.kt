package vitorsb.project.logidataprocess.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.exception.InvalidFileTypeException
import vitorsb.project.logidataprocess.service.OrderService

@RestController
@RequestMapping("/api/orders")
class OrderController {
    @Autowired
    private lateinit var service: OrderService

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

    @GetMapping("/{userId}/{orderId}")
    fun findByUserIdAndExternalId(
        @PathVariable userId: Long,
        @PathVariable orderId: Long
    ): ResponseEntity<OrderResponseDTO> {
        return ResponseEntity.ok(service.findByUserIdAndExternalId(userId, orderId))
    }

    @GetMapping("/{userId}")
    fun findOrders(
        @PathVariable userId: Long,
        @RequestParam startDate: String?,
        @RequestParam endDate: String?
    ): ResponseEntity<MutableList<OrderResponseDTO>> {
        return ResponseEntity.ok(service.findOrdersByUserId(userId, startDate, endDate))
    }
}