package dev.qrivi.fapp.server.persistence.entity

import dev.qrivi.fapp.server.persistence.Identifiable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(

    @Column(name = "name")
    var name: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @ManyToMany
    @JoinTable(
        name = "subscriptions",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "feed_id", referencedColumnName = "id")]
    )
    val subscriptions: Set<Feed>,

    @ManyToMany
    @JoinTable(
        name = "read_items",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "item_id", referencedColumnName = "id")]
    )
    val readItems: Set<Feed>

) : Identifiable()

enum class UserStatus {
    JOINED, // Signed up and took no further action
    CONFIRMED, // Did confirm his e-mail address
    BLOCKED // Has been very naughty
}
