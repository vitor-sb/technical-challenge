package vitorsb.project.logidataprocess.entity

import javax.persistence.*

@Entity
data class OrderProductRelation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    val product_id: Long,
    @Column(nullable = false)
    val order_id: Long
)
