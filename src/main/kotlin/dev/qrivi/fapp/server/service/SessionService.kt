package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.constant.SecurityConstants
import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.persistence.entity.Session
import dev.qrivi.fapp.server.persistence.repository.SessionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class SessionService(private val sessionRepository: SessionRepository) {

    fun getSession(token: String): Session? {
        return sessionRepository.findByToken(token)
    }

    fun createSession(account: Account, description: String): Session {
        val now = LocalDateTime.now()
        val session = Session(
            account = account,
            token = UUID.randomUUID().toString(),
            description = description,
            firstActive = now,
            lastActive = now
        )
        return sessionRepository.save(session)
    }

    fun refreshSession(session: Session, description: String?): Session? {
        val now = LocalDateTime.now()
        if (session.lastActive.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).isBefore(now)) {
            // token too old -- not safe.
            this.deleteSession(session)
            return null
        }

        description?.let { session.description = description }
        session.lastActive = now
        return sessionRepository.save(session)
    }

    fun deleteSession(session: Session) {
        sessionRepository.delete(session)
    }
}
