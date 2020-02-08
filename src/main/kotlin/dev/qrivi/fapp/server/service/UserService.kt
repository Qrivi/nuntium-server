package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.constants.SecurityConstants
import dev.qrivi.fapp.server.model.Token
import dev.qrivi.fapp.server.model.User
import dev.qrivi.fapp.server.repository.UserRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUser(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getUserWithPassword(email: String, password: String): User? {
        val user = this.getUser(email)
        if (user != null && BCrypt.checkpw(user.password, hashPassword(password)))
            return user
        return null
    }

    fun getUserWithToken(email: String, token: String): User? {
        val user = this.getUser(email)
        if (user != null && user.tokens.map { it.value }.contains(token))
            return user
        return null
    }

    fun createUser(email: String, name: String, password: String): User {
        val user = User(
                email = email,
                name = name,
                password = this.hashPassword(password)
        )
        return userRepository.save(user)
    }

    fun addToken(user: User, tokenDescription: String): Token {
        val token = Token(tokenDescription)
        user.tokens.add(token)
        userRepository.save(user)
        return token
    }

    fun refreshToken(user: User, tokenValue: String): Token? {
        val token = user.tokens.find { it.value == tokenValue }
                ?: return null
        if (token.generated.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).isBefore(Instant.now()))
            return null // we won't refresh expired refresh tokens

        token.generated = Instant.now()
        userRepository.save(user)
        return token
    }

    private fun hashPassword(password: String): String {
        val salt: String = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }
}
