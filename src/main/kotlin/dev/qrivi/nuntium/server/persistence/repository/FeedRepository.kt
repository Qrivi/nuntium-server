package dev.qrivi.nuntium.server.persistence.repository

import dev.qrivi.nuntium.server.persistence.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long> {

    fun findFirstByUrl(url: String): Feed?
}
