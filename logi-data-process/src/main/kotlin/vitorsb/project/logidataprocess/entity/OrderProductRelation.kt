package vitorsb.project.logidataprocess.entity

import javax.persistence.*

@Entity
@Table(name = "order_product_relation")
data class OrderProductRelation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "product_id", nullable = false)
    val productId: Long,
    @Column(name = "order_id", nullable = false)
    val orderId: Long
)
