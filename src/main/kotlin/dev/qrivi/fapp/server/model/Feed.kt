package dev.qrivi.fapp.server.model

import dev.qrivi.fapp.server.common.Identifiable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "feed")
data class Feed(

    @Column(name = "title")
    val name: String,

    @Column(name = "url")
    val url: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "language")
    var language: String,

    @Column(name = "image")
    var image: String,

    @Column(name = "last_fetched")
    var lastFetched: LocalDateTime,

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    var status: FeedStatus

) : Identifiable()

enum class FeedStatus {
    ADDED, // Feed was just added to the database
    OK, // Feed was fetched and parsed successfully
    NOT_FOUND, // Feed url returned a 404
    FETCH_ERROR, // Feed url returned a 5xx HTTP error
    PARSE_ERROR // Feed is not fappable
}
