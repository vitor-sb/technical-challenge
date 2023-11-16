package vitorsb.project.logidataprocess.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn

@Entity
data class Product(
    @Id
    var id: Long? = null,
    @Column(nullable = false)
    val value: Double
)
