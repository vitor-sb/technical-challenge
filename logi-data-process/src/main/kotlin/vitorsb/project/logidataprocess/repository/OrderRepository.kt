package vitorsb.project.logidataprocess.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import vitorsb.project.logidataprocess.entity.AccountOrder
import java.time.LocalDate

@Repository
interface OrderRepository: JpaRepository<AccountOrder, Long> {
    fun findByExternalIdAndUserId(externalId: Long, userId:Long): AccountOrder?
    fun findByUserId(userId: Long): List<AccountOrder>

    @Query("SELECT o FROM AccountOrder o WHERE o.userId = :userId AND o.purchaseDate >= :initialDate AND o.purchaseDate <= :finalDate")
    fun findByUserIdAndPurchaseDateBetween(
        @Param("userId") userId: Long,
        @Param("initialDate") initialDate: LocalDate,
        @Param("finalDate")finalDate: LocalDate
    ): List<AccountOrder>

    @Query("SELECT o FROM AccountOrder o WHERE o.userId = :userId AND o.purchaseDate <= :finalDate")
    fun findByUserIdAndPurchaseDateUntil(userId: Long, finalDate: LocalDate): List<AccountOrder>
}