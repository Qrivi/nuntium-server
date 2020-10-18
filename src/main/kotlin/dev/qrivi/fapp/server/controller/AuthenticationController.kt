package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.controller.dto.req.AuthWithPasswordDTO
import dev.qrivi.fapp.server.controller.dto.req.AuthWithTokenDTO
import dev.qrivi.fapp.server.controller.dto.req.RegisterUserDTO
import dev.qrivi.fapp.server.controller.dto.res.Response
import dev.qrivi.fapp.server.controller.dto.res.error.BadRequest
import dev.qrivi.fapp.server.controller.dto.res.error.Unauthorized
import dev.qrivi.fapp.server.service.TokenService
import dev.qrivi.fapp.server.service.UserService
import dev.qrivi.fapp.server.util.ClientAnalyzer
import dev.qrivi.fapp.server.util.generateAccessToken
import dev.qrivi.fapp.server.util.generateResponse
import dev.qrivi.fapp.server.util.toAuthenticatedUser
import dev.qrivi.fapp.server.util.toNewUser
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
@RequestMapping("/auth")
class AuthenticationController(
    private val userService: UserService,
    private val tokenService: TokenService,
    private val clientAnalyzer: ClientAnalyzer
) {

    @PostMapping("/register")
    fun registerUser(
        @RequestHeader(value = "User-Agent") userAgent: String,
        @Valid @RequestBody dto: RegisterUserDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        if (userService.getUser(dto.email) != null)
            return generateResponse(BadRequest(error = "A user was already registered with ${dto.email}"))

        val user = userService.createUser(dto.email, dto.name ?: "Human", dto.password)
        val token = tokenService.createToken(user, dto.client ?: clientAnalyzer.getFromUserAgent(userAgent))
        return generateResponse(user.toNewUser(generateAccessToken(user, token)))
    }

    @PostMapping("/login")
    fun authWithPassword(
        @RequestHeader(value = "User-Agent") userAgent: String,
        @Valid @RequestBody dto: AuthWithPasswordDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val user = userService.getUserWithPassword(dto.email, dto.password)
            ?: return generateResponse(BadRequest(error = "Unregistered user or invalid password"))

        val token = tokenService.createToken(user, dto.client ?: clientAnalyzer.getFromUserAgent(userAgent))
        return generateResponse(user.toAuthenticatedUser(generateAccessToken(user, token)))
    }

    @PostMapping("/refresh")
    fun authWithToken(
        @RequestHeader(value = "User-Agent") userAgent: String,
        @Valid @RequestBody dto: AuthWithTokenDTO,
        res: BindingResult,
        req: HttpServletRequest
    ): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val user = userService.getUser(dto.email)
            ?: return generateResponse(BadRequest(error = "Unregistered user"))

        var token = tokenService.getToken(dto.token)
        if (token?.user != user) // token is null or token user does not match the requested user
            return generateResponse(BadRequest(error = "Invalid refresh token"))

        token = tokenService.refreshToken(token, dto.client)
            ?: return generateResponse(Unauthorized(reason = Unauthorized.Reason.EXPIRED_ACCESS_TOKEN, realm = req.serverName))

        return generateResponse(user.toAuthenticatedUser(generateAccessToken(user, token)))
    }
}
