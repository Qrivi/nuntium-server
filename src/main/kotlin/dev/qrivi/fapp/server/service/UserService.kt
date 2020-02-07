package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.constants.SecurityConstants
import dev.qrivi.fapp.server.model.Token
import dev.qrivi.fapp.server.model.User
import dev.qrivi.fapp.server.repository.UserRepository
import java.time.LocalDateTime
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
        if (user != null && user.tokens.map { it.token }.contains(token))
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

    fun generateNewTokenForUser(user: User, description: String): Token {
        val token = Token(description)
        user.tokens.add(token)
        userRepository.save(user)
        return token
    }

    fun validateToken(user: User, token: String): Boolean {
        return user.tokens.find { it.token == token }!!.generated
                .plusHours(SecurityConstants.REFRESH_TTL)
                .isAfter(LocalDateTime.now())
    }

    private fun hashPassword(password: String): String {
        val salt: String = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }
}
