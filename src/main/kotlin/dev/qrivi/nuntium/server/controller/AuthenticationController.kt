package dev.qrivi.nuntium.server.controller

import dev.qrivi.nuntium.server.constant.SecurityConstants
import dev.qrivi.nuntium.server.controller.dto.req.AuthWithPasswordDTO
import dev.qrivi.nuntium.server.controller.dto.req.AuthWithTokenDTO
import dev.qrivi.nuntium.server.controller.dto.req.RegisterAccountDTO
import dev.qrivi.nuntium.server.controller.dto.res.Response
import dev.qrivi.nuntium.server.controller.dto.res.error.BadRequest
import dev.qrivi.nuntium.server.controller.dto.res.error.Unauthorized
import dev.qrivi.nuntium.server.service.AccountService
import dev.qrivi.nuntium.server.service.SessionService
import dev.qrivi.nuntium.server.util.ClientAnalyzer
import dev.qrivi.nuntium.server.util.generateAccessToken
import dev.qrivi.nuntium.server.util.generateResponse
import dev.qrivi.nuntium.server.util.toAuthenticationResponse
import dev.qrivi.nuntium.server.util.toNewAccountResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping(SecurityConstants.AUTH_ROUTE)
class AuthenticationController(
    private val accountService: AccountService,
    private val sessionService: SessionService,
    private val clientAnalyzer: ClientAnalyzer
) {

    @PostMapping("/register")
    fun register(
        @RequestHeader(value = HttpHeaders.USER_AGENT) userAgent: String,
        @Valid @RequestBody dto: RegisterAccountDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        if (accountService.getAccount(dto.email) != null)
            return generateResponse(BadRequest(error = "An account was already registered with ${dto.email}"))

        val account = accountService.createAccount(dto.email, dto.password, dto.name)
        val session = sessionService.createSession(account, dto.client ?: clientAnalyzer.getFromUserAgent(userAgent))
        return generateResponse(account.toNewAccountResponse(generateAccessToken(account, session, req.serverName)))
    }

    @PostMapping("/login")
    fun authWithPassword(
        @RequestHeader(value = HttpHeaders.USER_AGENT) userAgent: String,
        @Valid @RequestBody dto: AuthWithPasswordDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val account = accountService.getAccountWithPassword(dto.email, dto.password)
            ?: return generateResponse(BadRequest(error = "Unregistered account or invalid password"))

        val session = sessionService.createSession(account, dto.client ?: clientAnalyzer.getFromUserAgent(userAgent))
        return generateResponse(account.toAuthenticationResponse(generateAccessToken(account, session, req.serverName)))
    }

    @PostMapping("/refresh")
    fun authWithToken(
        @RequestHeader(value = HttpHeaders.USER_AGENT) userAgent: String,
        @Valid @RequestBody dto: AuthWithTokenDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val account = accountService.getAccount(dto.email)
            ?: return generateResponse(BadRequest(error = "Unregistered account"))

        var session = sessionService.getSession(dto.token)
        if (session?.account != account) // token is null or token account does not match the requested account
            return generateResponse(BadRequest(error = "Invalid refresh token"))

        session = sessionService.refreshSession(session, dto.client)
            ?: return generateResponse(Unauthorized(reason = Unauthorized.Reason.EXPIRED_ACCESS_TOKEN, realm = req.serverName))

        return generateResponse(account.toAuthenticationResponse(generateAccessToken(account, session, req.serverName)))
    }
}
