package dev.qrivi.nuntium.server.persistence.repository

import dev.qrivi.nuntium.server.persistence.entity.Account
import dev.qrivi.nuntium.server.persistence.entity.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {

    fun findByAccount(account: Account): List<Session>

    fun findByToken(token: String): Session?
}
