package vitorsb.project.logidataprocess.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import vitorsb.project.logidataprocess.entity.Order
import java.time.LocalDate

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByExternalIdAndAndUserId(externalId: Long, userId:Long): Order?
    fun findByUserId(userId: Long): List<Order>

    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.purchaseDate >= :initialDate AND o.purchaseDate <= :finalDate")
    fun findByUserIdAndPurchaseDateBetween(
        @Param("userId") userId: Long,
        @Param("initialDate") initialDate: LocalDate,
        @Param("finalDate")finalDate: LocalDate
    ): List<Order>
}