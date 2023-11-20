package fixtures

import vitorsb.project.logidataprocess.dto.order.OrderTxtFileResponseDTO
import vitorsb.project.logidataprocess.dto.product.ProductTxtFileResponseDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import java.time.LocalDate

internal object DataFixtureFactory {
    object ValidDataResponses {
        fun validOneUserWithOneOrderAndThreeProducts(): MutableList<UserTxtFileResponseDTO> {
            val products = mutableListOf(
                ProductTxtFileResponseDTO(
                    product_id = 3,
                    value = 1899.02
                ),
                ProductTxtFileResponseDTO(
                    product_id = 6,
                    value = 224.75,
                ),
                ProductTxtFileResponseDTO(
                    product_id = 1,
                    value = 1760.01,
                )
            )

            val order = OrderTxtFileResponseDTO(
                order_id = 836,
                products = products,
                date = LocalDate.parse("2021-09-09"),
                total = products.sumOf { it.value }
            )

            val user = UserTxtFileResponseDTO(
                user_id = 88,
                name = "Terra Daniel DDS",
                orders = mutableListOf(order)
            )

            return mutableListOf(user)
        }

        fun validOneUserWithTwoOrdersAndProducts(): MutableList<UserTxtFileResponseDTO> {
            val firstOrderProducts = mutableListOf(
                ProductTxtFileResponseDTO(
                    product_id = 3,
                    value = 1899.02
                ),
                ProductTxtFileResponseDTO(
                    product_id = 6,
                    value = 224.75,
                ),
            )

            val secondOrderProducts = mutableListOf(
                ProductTxtFileResponseDTO(
                    product_id = 1,
                    value = 1760.01,
                )
            )

            val firstOrder = OrderTxtFileResponseDTO(
                order_id = 836,
                products = firstOrderProducts,
                date = LocalDate.parse("2021-09-09"),
                total = firstOrderProducts.sumOf { it.value }
            )

            val secondOrder = OrderTxtFileResponseDTO(
                order_id = 837,
                products = secondOrderProducts,
                date = LocalDate.parse("2021-11-22"),
                total = secondOrderProducts.sumOf { it.value }
            )

            val user = UserTxtFileResponseDTO(
                user_id = 88,
                name = "Terra Daniel DDS",
                orders = mutableListOf(firstOrder, secondOrder)
            )

            return mutableListOf(user)
        }

        fun validThreeUsersWithOrderAndProduct(): MutableList<UserTxtFileResponseDTO> {
            val firstProduct = ProductTxtFileResponseDTO(
                product_id = 3,
                value = 1899.02
            )

            val firstUser = UserTxtFileResponseDTO(
                user_id = 88,
                name = "Terra Daniel DDS",
                orders = mutableListOf(
                    OrderTxtFileResponseDTO(
                        order_id = 836,
                        products = mutableListOf(firstProduct),
                        date = LocalDate.parse("2021-09-09"),
                        total = firstProduct.value
                    )
                )
            )

            val secondProduct = ProductTxtFileResponseDTO(
                product_id = 6,
                value = 224.75
            )

            val secondUser = UserTxtFileResponseDTO(
                user_id = 83,
                name = "Frances Satterfield",
                orders = mutableListOf(
                    OrderTxtFileResponseDTO(
                        order_id = 791,
                        products = mutableListOf(secondProduct),
                        date = LocalDate.parse("2021-11-22"),
                        total = secondProduct.value
                    )
                )
            )

            val thirdProduct = ProductTxtFileResponseDTO(
                product_id = 1,
                value = 1760.01
            )

            val thirdUser = UserTxtFileResponseDTO(
                user_id = 141,
                name = "Lloyd Mante",
                orders = mutableListOf(
                    OrderTxtFileResponseDTO(
                        order_id = 1304,
                        products = mutableListOf(thirdProduct),
                        date = LocalDate.parse("2021-10-12"),
                        total = thirdProduct.value
                    )
                )
            )

            return mutableListOf(firstUser, secondUser, thirdUser)
        }
    }

    object InvalidDataParameters {
        val oneUserWithInvalidId =
            "00000oito8                             Terra Daniel DDS00000008360000000003     1899.0220210909"
        val oneUserWithInvalidPurchaseDate =
            "0000000008                             Terra Daniel DDS00000008360000000003     1899.0220211331"
        val oneUserWithInvalidProductValue =
            "0000000008                             Terra Daniel DDS00000008360000000003     18 9.0220210909"
        val invalidDateValue = "20211331"
    }
}