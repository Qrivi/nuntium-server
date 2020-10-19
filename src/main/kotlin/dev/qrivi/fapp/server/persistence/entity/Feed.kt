package dev.qrivi.fapp.server.persistence.entity

import dev.qrivi.fapp.server.persistence.Identifiable
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "feed")
open class Feed(

    @Column(name = "title")
    open var name: String,

    @Column(name = "url")
    open var url: String,

    @Column(name = "description")
    open var description: String,

    @Column(name = "language")
    open var language: String,

    @Column(name = "image")
    open var image: String,

    @Column(name = "last_fetched")
    open var lastFetched: ZonedDateTime,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    open var status: FeedStatus

) : Identifiable()

enum class FeedStatus {
    ADDED, // Feed was just added to the database
    OK, // Feed was fetched and parsed successfully
    NOT_FOUND, // Feed url returned a 404
    FETCH_ERROR, // Feed url returned a 5xx HTTP error
    PARSE_ERROR // Feed is not fappable
}
