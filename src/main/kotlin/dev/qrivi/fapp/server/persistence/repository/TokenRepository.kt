package dev.qrivi.fapp.server.persistence.repository

import dev.qrivi.fapp.server.persistence.entity.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Token, Long> {

    fun findByValue(value: String): Token?
}
