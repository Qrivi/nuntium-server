package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.controller.dto.res.Response
import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.service.SessionService
import dev.qrivi.fapp.server.util.generateResponse
import dev.qrivi.fapp.server.util.toSessionResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val sessionService: SessionService
) {

    @GetMapping("/sessions")
    fun getSessions(
        @AuthenticationPrincipal account: Account,
    ): ResponseEntity<Response> {
        return generateResponse(sessionService.getSessionsForAccount(account).toSessionResponse())
    }
}
