package dev.qrivi.fapp.server.service

import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.persistence.entity.AccountStatus
import dev.qrivi.fapp.server.persistence.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun getAccount(email: String): Account? {
        return accountRepository.findByEmail(email)
    }

    fun getAccountWithPassword(email: String, password: String): Account? {
        val account = this.getAccount(email)
        if (account != null && BCrypt.checkpw(password, account.password))
            return account
        return null
    }

    fun createAccount(email: String, name: String, password: String): Account {
        val account = Account(
            email = email,
            name = name,
            password = this.hashPassword(password),
            status = AccountStatus.JOINED,
            subscriptions = mutableSetOf(),
            readItems = mutableSetOf()
        )
        return accountRepository.save(account)
    }

    private fun hashPassword(password: String): String {
        val salt: String = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }
}
