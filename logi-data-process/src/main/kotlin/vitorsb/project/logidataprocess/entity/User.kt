package vitorsb.project.logidataprocess.entity

import javax.persistence.*

@Entity
data class User(
    @Id @Column(nullable = false)
    var id: Long,

    @Column(nullable = false, length = 45)
    val name: String,
)