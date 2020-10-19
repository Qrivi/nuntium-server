package dev.qrivi.fapp.server.persistence.entity

import dev.qrivi.fapp.server.persistence.Identifiable
import java.time.LocalDateTime
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

    @Column(name = "first_active")
    open var firstActive: LocalDateTime,

    @Column(name = "last_active")
    open var lastActive: LocalDateTime

) : Identifiable()
