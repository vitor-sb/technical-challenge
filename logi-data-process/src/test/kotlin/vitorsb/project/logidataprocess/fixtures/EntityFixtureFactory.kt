package vitorsb.project.logidataprocess.fixtures

import vitorsb.project.logidataprocess.entity.Order
import vitorsb.project.logidataprocess.entity.Product
import vitorsb.project.logidataprocess.entity.User
import java.time.LocalDate

internal object EntityFixtureFactory {
    object ValidEntities {
        fun validUser(
            id: Long = 88,
            name: String = "Terra Daniel DDS"
        ): User {
            return User(
                id = id,
                name = name
            )
        }

        fun validOrder(
            id: Long = 1,
            purchaseDate: LocalDate = LocalDate.parse("2021-09-09"),
            externalId: Long = 836,
            userId: Long = 88,
            user: User = validUser()
        ): Order {
            return Order(
                id = id,
                purchaseDate = purchaseDate,
                externalId = externalId,
                userId = userId,
                user = user
            )
        }

        fun validProduct(
            id: Long = 1,
            externalId: Long = 3,
            value: Double = 1899.02,
        ): Product {
            return Product(
                id = id,
                externalId = externalId,
                value = value
            )
        }
    }
}