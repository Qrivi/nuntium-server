package dev.qrivi.nuntium.server.persistence.entity

import dev.qrivi.nuntium.server.persistence.Identifiable
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "item")
open class Item(

    @ManyToOne
    @JoinColumn(name = "feed_id")
    open var feed: Feed,

    @Column(name = "title")
    open var title: String,

    @Column(name = "url")
    open var url: String,

    @Column(name = "guid")
    open var guid: String,

    @Column(name = "published")
    open var published: ZonedDateTime,

    @Column(name = "description")
    open var description: String

) : Identifiable()
