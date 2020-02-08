package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.dto.req.AuthWithPasswordDTO
import dev.qrivi.fapp.server.dto.req.AuthWithTokenDTO
import dev.qrivi.fapp.server.dto.res.Response
import dev.qrivi.fapp.server.dto.res.error.BadRequest
import dev.qrivi.fapp.server.dto.res.error.Unauthorized
import dev.qrivi.fapp.server.service.UserService
import dev.qrivi.fapp.server.util.generateAccessToken
import dev.qrivi.fapp.server.util.generateResponse
import dev.qrivi.fapp.server.util.toAuthenticatedUser
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthenticationController(private val userService: UserService) {

    @PostMapping("/")
    fun authWithPassword(@Valid @RequestBody dto: AuthWithPasswordDTO, res: BindingResult, req: HttpServletRequest): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val user = userService.getUserWithPassword(dto.email, dto.password)
                ?: return generateResponse(BadRequest(error = "Unregistered user or invalid password"))

        userService.addToken(user)
    }

    @PostMapping("/refresh")
    fun authWithToken(@Valid @RequestBody dto: AuthWithTokenDTO, res: BindingResult, req: HttpServletRequest): ResponseEntity<Response> {
        if (res.hasErrors())
            return generateResponse(BadRequest(errors = res.allErrors.map { it.defaultMessage as String }))

        val user = userService.getUserWithToken(dto.email, dto.token)
                ?: return generateResponse(BadRequest(error = "Unregistered user or invalid refresh token"))

        val token = userService.refreshToken(user, dto.token)
                ?: return generateResponse(Unauthorized(reason = Unauthorized.Reason.EXPIRED_ACCESS_TOKEN, realm = req.serverName))

        return generateResponse(user.toAuthenticatedUser(generateAccessToken(user, token)))
    }
}
