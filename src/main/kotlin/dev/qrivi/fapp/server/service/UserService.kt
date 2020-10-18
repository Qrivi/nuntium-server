package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.persistence.entity.User
import dev.qrivi.fapp.server.persistence.entity.UserStatus
import dev.qrivi.fapp.server.persistence.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUser(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getUserWithPassword(email: String, password: String): User? {
        val user = this.getUser(email)
        if (user != null && BCrypt.checkpw(password, user.password))
            return user
        return null
    }

    fun createUser(email: String, name: String, password: String): User {
        val user = User(
            email = email,
            name = name,
            password = this.hashPassword(password),
            status = UserStatus.JOINED,
            subscriptions = emptySet(),
            readItems = emptySet()
        )
        return userRepository.save(user)
    }

    private fun hashPassword(password: String): String {
        val salt: String = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }
}
