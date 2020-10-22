package dev.qrivi.fapp.server.persistence.repository

import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.persistence.entity.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {

    fun findByAccount(account: Account): List<Session>

    fun findByToken(token: String): Session?
}
