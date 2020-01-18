package dev.qrivi.fapp.server.repository

import dev.qrivi.fapp.server.model.Feed
import org.springframework.data.mongodb.repository.MongoRepository

interface FeedRepository : MongoRepository<Feed, String> {
    fun findFirstByUrl(url: String): Feed?
}
