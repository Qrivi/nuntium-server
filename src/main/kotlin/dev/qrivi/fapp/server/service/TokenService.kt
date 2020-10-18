package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.constant.SecurityConstants
import dev.qrivi.fapp.server.persistence.entity.Token
import dev.qrivi.fapp.server.persistence.entity.User
import dev.qrivi.fapp.server.persistence.repository.TokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class TokenService(private val tokenRepository: TokenRepository) {

    fun getToken(value: String): Token? {
        return tokenRepository.findByValue(value)
    }

    fun createToken(user: User, description: String): Token {
        val now = LocalDateTime.now()
        val token = Token(
            user = user,
            value = UUID.randomUUID().toString(),
            description = description,
            firstUsed = now,
            lastUsed = now
        )
        return tokenRepository.save(token)
    }

    fun refreshToken(token: Token, description: String?): Token? {
        val now = LocalDateTime.now()
        if (token.lastUsed.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).isBefore(now)) {
            // token too old -- not safe.
            this.deleteToken(token)
            return null
        }

        description?.let { token.description = description }
        token.lastUsed = now
        return tokenRepository.save(token)
    }

    fun deleteToken(token: Token) {
        tokenRepository.delete(token)
    }
}
