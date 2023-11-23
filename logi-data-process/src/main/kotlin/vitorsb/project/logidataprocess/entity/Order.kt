package vitorsb.project.logidataprocess.entity

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "purchase_date")
    @NotNull
    val purchaseDate: LocalDate,

    @Column(name = "external_id", nullable = false)
    val externalId: Long,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Transient
    var account: Account? = null,

    @Transient
    var products: MutableList<Product> = ArrayList()
)
