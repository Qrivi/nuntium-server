package dev.qrivi.nuntium.server.persistence.entity

import dev.qrivi.nuntium.server.persistence.Identifiable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "account")
open class Account(

    @Column(name = "uuid")
    open var uuid: String,

    @Column(name = "email")
    open var email: String,

    @Column(name = "password")
    open var password: String,

    @Column(name = "name")
    open var name: String?,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    open var status: AccountStatus,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "subscriptions",
        joinColumns = [JoinColumn(name = "account_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "feed_id", referencedColumnName = "id")]
    )
    open var subscriptions: MutableSet<Feed>,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "read_items",
        joinColumns = [JoinColumn(name = "account_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "item_id", referencedColumnName = "id")]
    )
    open var readItems: MutableSet<Feed>

) : Identifiable()

enum class AccountStatus {
    JOINED, // Signed up and took no further action
    CONFIRMED, // Did confirm his e-mail address
    BLOCKED // Has been very naughty
}
