package dev.qrivi.nuntium.server.service

import dev.qrivi.nuntium.server.constant.SecurityConstants
import dev.qrivi.nuntium.server.persistence.entity.Account
import dev.qrivi.nuntium.server.persistence.entity.Session
import dev.qrivi.nuntium.server.persistence.repository.SessionRepository
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class SessionService(private val sessionRepository: SessionRepository) {

    fun getSession(token: String): Session? {
        return sessionRepository.findByToken(token)
    }

    fun getSessionsForAccount(account: Account): List<Session> {
        return sessionRepository.findByAccount(account)
    }

    fun createSession(account: Account, description: String): Session {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val session = Session(
            account = account,
            token = UUID.randomUUID().toString(),
            description = description,
            firstLogin = now,
            lastLogin = now
        )
        return sessionRepository.save(session)
    }

    fun refreshSession(session: Session, description: String?): Session? {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        if (session.lastLogin.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).isBefore(now)) {
            // token too old -- not safe.
            this.deleteSession(session)
            return null
        }

        description?.let { session.description = description }
        session.lastLogin = now
        return sessionRepository.save(session)
    }

    fun deleteSession(session: Session) {
        sessionRepository.delete(session)
    }
}
