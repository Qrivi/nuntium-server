package dev.qrivi.nuntium.server.persistence.entity

import dev.qrivi.nuntium.server.persistence.Identifiable
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "session")
open class Session(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    open var account: Account,

    @Column(name = "token")
    open var token: String,

    @Column(name = "description")
    open var description: String,

    @Column(name = "first_login")
    open var firstLogin: ZonedDateTime,

    @Column(name = "last_login")
    open var lastLogin: ZonedDateTime

) : Identifiable()
