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

    @ResponseBody
    @GetMapping("/byUserId")
    fun findOrdersByUserId(
        @RequestParam userId: Long,
    ): ResponseEntity<MutableList<OrderResponseDTO>> {
        return ResponseEntity.ok(service.findOrdersByUserId(userId))
    }

    @ResponseBody
    @GetMapping("/byUserIdAndOrderId")
    fun findOrdersByUserIdAndExternalId(
        @RequestParam userId: Long,
        @RequestParam orderId: Long
    ): ResponseEntity<OrderResponseDTO> {
        return ResponseEntity.ok(service.findByUserIdAndExternalId(userId, orderId))
    }

    @ResponseBody
    @GetMapping("/byUserIdAndPurchaseDateBetween")
    fun findOrdersByUserIdAndPurchaseDateBetween(
        @RequestParam userId: Long,
        @RequestParam startDate: String?,
        @RequestParam endDate: String?
    ): ResponseEntity<MutableList<OrderResponseDTO>> {
        return ResponseEntity.ok(service.findByUserIdAndPurchaseDateBetween(userId, startDate, endDate))
    }

    @ResponseBody
    @PostMapping("/processTxtFile")
    fun processTxtFile(
        @RequestParam file: MultipartFile,
        @RequestParam(required = false) toSave: Boolean? = false
    ): ResponseEntity<MutableList<UserTxtFileResponseDTO>> {
        if (!file.contentType.equals("text/plain"))
            throw InvalidFileTypeException("M=processTxtFile - File The file must be text type")

        val response = service.processTxtFile(file, toSave)

        return ResponseEntity.ok(response)
    }
}