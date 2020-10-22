package dev.qrivi.fapp.server.persistence.repository

import dev.qrivi.fapp.server.persistence.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUuid(uuid: String): Account?

    fun findByEmail(email: String): Account?
}
