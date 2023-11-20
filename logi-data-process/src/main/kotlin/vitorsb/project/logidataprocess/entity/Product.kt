package vitorsb.project.logidataprocess.entity

import javax.persistence.*

@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var externalId: Long,
    @Column(nullable = false)
    val value: Double
)
