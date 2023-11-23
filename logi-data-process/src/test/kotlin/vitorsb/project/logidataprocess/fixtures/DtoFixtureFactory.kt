package vitorsb.project.logidataprocess.fixtures

import vitorsb.project.logidataprocess.dto.order.OrderResponseDTO
import vitorsb.project.logidataprocess.dto.product.ProductResponseDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import java.time.LocalDate

internal object DtoFixtureFactory {
    object ValidDtos {

        fun validOrderResponseDTO(
            orderId: Long = 836,
            date: LocalDate = LocalDate.parse("2021-09-09"),
            products: MutableList<ProductResponseDTO> = mutableListOf(
                validProductResponseDTO()
            ),
        ): OrderResponseDTO {
            return OrderResponseDTO(
                order_id = orderId,
                date = date,
                products = products,
                total = products.sumOf { it.value }
            )
        }
        fun validProductResponseDTO(
            productId: Long = 3,
            value: Double = 1899.02
        ): ProductResponseDTO {
            return ProductResponseDTO(
                product_id = productId,
                value = value
            )
        }

        fun validOneUserWithOneOrderAndThreeProducts(): MutableList<UserTxtFileResponseDTO> {
            val products = mutableListOf(
                ProductResponseDTO(
                    product_id = 3,
                    value = 1899.02
                ),
                ProductResponseDTO(
                    product_id = 6,
                    value = 224.75,
                ),
                ProductResponseDTO(
                    product_id = 1,
                    value = 1760.01,
                )
            )

            val order = OrderResponseDTO(
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
                ProductResponseDTO(
                    product_id = 3,
                    value = 1899.02
                ),
                ProductResponseDTO(
                    product_id = 6,
                    value = 224.75,
                ),
            )

            val secondOrderProducts = mutableListOf(
                ProductResponseDTO(
                    product_id = 1,
                    value = 1760.01,
                )
            )

            val firstOrder = OrderResponseDTO(
                order_id = 836,
                products = firstOrderProducts,
                date = LocalDate.parse("2021-09-09"),
                total = firstOrderProducts.sumOf { it.value }
            )

            val secondOrder = OrderResponseDTO(
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
            val firstProduct = ProductResponseDTO(
                product_id = 3,
                value = 1899.02
            )

            val firstUser = UserTxtFileResponseDTO(
                user_id = 88,
                name = "Terra Daniel DDS",
                orders = mutableListOf(
                    OrderResponseDTO(
                        order_id = 836,
                        products = mutableListOf(firstProduct),
                        date = LocalDate.parse("2021-09-09"),
                        total = firstProduct.value
                    )
                )
            )

            val secondProduct = ProductResponseDTO(
                product_id = 6,
                value = 224.75
            )

            val secondUser = UserTxtFileResponseDTO(
                user_id = 83,
                name = "Frances Satterfield",
                orders = mutableListOf(
                    OrderResponseDTO(
                        order_id = 791,
                        products = mutableListOf(secondProduct),
                        date = LocalDate.parse("2021-11-22"),
                        total = secondProduct.value
                    )
                )
            )

            val thirdProduct = ProductResponseDTO(
                product_id = 1,
                value = 1760.01
            )

            val thirdUser = UserTxtFileResponseDTO(
                user_id = 141,
                name = "Lloyd Mante",
                orders = mutableListOf(
                    OrderResponseDTO(
                        order_id = 1304,
                        products = mutableListOf(thirdProduct),
                        date = LocalDate.parse("2021-10-12"),
                        total = thirdProduct.value
                    )
                )
            )

            return mutableListOf(firstUser, secondUser, thirdUser)
        }
        fun validTwoUsersWithOrderAndProduct(): MutableList<UserTxtFileResponseDTO> {
            val firstProduct = ProductResponseDTO(
                product_id = 3,
                value = 1899.02
            )

            val firstUser = UserTxtFileResponseDTO(
                user_id = 88,
                name = "Terra Daniel DDS",
                orders = mutableListOf(
                    OrderResponseDTO(
                        order_id = 836,
                        products = mutableListOf(firstProduct),
                        date = LocalDate.parse("2021-09-09"),
                        total = firstProduct.value
                    )
                )
            )

            val secondProduct = ProductResponseDTO(
                product_id = 1,
                value = 1760.01
            )

            val secondUser = UserTxtFileResponseDTO(
                user_id = 89,
                name = "Carlos Marco DDS",
                orders = mutableListOf(
                    OrderResponseDTO(
                        order_id = 837,
                        products = mutableListOf(secondProduct),
                        date = LocalDate.parse("2021-09-09"),
                        total = secondProduct.value
                    )
                )
            )

            return mutableListOf(firstUser, secondUser)
        }
    }
}