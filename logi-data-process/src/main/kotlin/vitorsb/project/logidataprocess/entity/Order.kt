package vitorsb.project.logidataprocess.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "user_order")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val purchaseDate: LocalDate,

    val externalId: Long,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToMany
    @JoinTable(
        name = "order_product_relation",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableList<Product> = ArrayList()
)
