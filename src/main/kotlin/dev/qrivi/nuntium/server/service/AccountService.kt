package dev.qrivi.nuntium.server.service

import dev.qrivi.nuntium.server.persistence.entity.Account
import dev.qrivi.nuntium.server.persistence.entity.AccountStatus
import dev.qrivi.nuntium.server.persistence.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun getByUuid(uuid: String): Account? {
        return accountRepository.findByUuid(uuid)
    }

    fun getAccount(email: String): Account? {
        return accountRepository.findByEmail(email)
    }

    fun getAccountWithPassword(email: String, password: String): Account? {
        val account = this.getAccount(email)
        if (account != null && BCrypt.checkpw(password, account.password))
            return account
        return null
    }

    fun createAccount(email: String, password: String, name: String?): Account {
        val account = Account(
            uuid = UUID.randomUUID().toString(),
            email = email,
            name = name,
            password = this.hashPassword(password),
            status = AccountStatus.JOINED,
            subscriptions = mutableSetOf(),
            readItems = mutableSetOf()
        )
        return accountRepository.save(account)
    }

    fun updateAccount(account: Account, email: String?, password: String?, name: String?): Account {
        email?.let { account.email = it }
        password?.let { account.password = it }
        name?.let { account.name = it }

        return accountRepository.save(account)
    }

    private fun hashPassword(password: String): String {
        val salt: String = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }
}
