package vitorsb.project.logidataprocess.fixtures

import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import java.time.LocalDate

internal object DataFixtureFactory {
    object InvalidDatas {
        val oneUserWithInvalidId =
            "00000oito8                             Terra Daniel DDS00000008360000000003     1899.0220210909"
        val oneUserWithInvalidPurchaseDate =
            "0000000008                             Terra Daniel DDS00000008360000000003     1899.0220211331"
        val oneUserWithInvalidProductValue =
            "0000000008                             Terra Daniel DDS00000008360000000003     18 9.0220210909"
        val invalidDateValue = "20211331"
    }
}