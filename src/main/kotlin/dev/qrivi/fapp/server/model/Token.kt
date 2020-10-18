package dev.qrivi.fapp.server.model

import dev.qrivi.fapp.server.common.Identifiable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "token")
data class Token(

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "value")
    val value: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "first_used")
    val firstUsed: LocalDateTime,

    @Column(name = "last_used")
    var lastUsed: LocalDateTime

) : Identifiable()
