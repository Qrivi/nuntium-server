package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.controller.dto.req.UpdateAccountDTO
import dev.qrivi.fapp.server.controller.dto.res.Response
import dev.qrivi.fapp.server.controller.dto.res.UpdatedEntityDTO
import dev.qrivi.fapp.server.controller.dto.res.error.BadRequest
import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.service.AccountService
import dev.qrivi.fapp.server.service.SessionService
import dev.qrivi.fapp.server.util.generateResponse
import dev.qrivi.fapp.server.util.toAccountResponse
import dev.qrivi.fapp.server.util.toSessionResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService,
    private val sessionService: SessionService
) {

    @GetMapping
    fun getAccount(
        @AuthenticationPrincipal account: Account,
    ): ResponseEntity<Response> {
        return generateResponse(account.toAccountResponse())
    }

    @PutMapping
    fun updateAccount(
        @AuthenticationPrincipal account: Account,
        @Valid @RequestBody dto: UpdateAccountDTO,
        res: BindingResult,
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val updatedFields = mutableListOf<String>()
        dto.email?.let { updatedFields.add("email") }
        dto.name?.let { updatedFields.add("name") }
        dto.password?.let { updatedFields.add("password") }

        val updatedEntity = accountService.updateAccount(
            account = account,
            email = dto.email,
            password = dto.password,
            name = dto.name
        ).toAccountResponse()

        return generateResponse(UpdatedEntityDTO(updatedFields, updatedEntity))
    }

    @GetMapping("/sessions")
    fun getSessions(
        @AuthenticationPrincipal account: Account,
    ): ResponseEntity<Response> {
        return generateResponse(sessionService.getSessionsForAccount(account).toSessionResponse())
    }
}
