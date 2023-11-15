package vitorsb.project.logidataprocess.entity

import javax.persistence.Column
import javax.persistence.Id

data class Product(
    @Id
    var id: Long? = null,
    @Column(nullable = false)
    val value: Double,
)
