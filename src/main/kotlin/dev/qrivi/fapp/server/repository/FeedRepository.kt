package dev.qrivi.fapp.server.repository

import dev.qrivi.fapp.server.model.Feed
import org.springframework.data.mongodb.repository.MongoRepository

interface FeedRepository : MongoRepository<Feed, Long> {
    fun findFirstByUrl(url: String): Feed?
}