package dev.qrivi.fapp.server.repository

import dev.qrivi.fapp.server.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, Long> {
    fun findByEmail(email: String): User?
}