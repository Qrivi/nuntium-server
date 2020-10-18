package dev.qrivi.fapp.server.persistence.repository

import dev.qrivi.fapp.server.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
}
