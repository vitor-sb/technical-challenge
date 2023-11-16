package vitorsb.project.logidataprocess.entity

import javax.persistence.*

@Entity
data class User(
    @Id
    var id: Long? = null,

    @Column(nullable = false, length = 45)
    val name: String,

    @OneToMany(mappedBy = "user")
    val orders: MutableList<Order>  = ArrayList()
)