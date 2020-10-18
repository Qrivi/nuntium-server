package dev.qrivi.fapp.server.persistence.entity

import dev.qrivi.fapp.server.persistence.Identifiable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "item")
data class Item(

    @ManyToOne
    @JoinColumn(name = "feed_id")
    val feed: Feed,

    @Column(name = "title")
    val title: String,

    @Column(name = "url")
    val url: String,

    @Column(name = "guid")
    val guid: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "published")
    val published: LocalDateTime,

    @Column(name = "content")
    val content: String

) : Identifiable()
