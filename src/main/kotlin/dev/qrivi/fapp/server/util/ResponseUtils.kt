package dev.qrivi.fapp.server.util

import dev.qrivi.fapp.server.dto.res.AuthenticatedUser
import dev.qrivi.fapp.server.dto.res.Response
import dev.qrivi.fapp.server.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun createResponse(response: Response): ResponseEntity<Response> {
    return ResponseEntity(response, response.httpHeaders, response.httpStatus)
}

fun User.toAuthenticatedUser(token: String) = AuthenticatedUser(
        email = email,
        name = name,
        token = token
)

fun User.toNewUser(token: String) = AuthenticatedUser(
        httpStatus = HttpStatus.CREATED,
        email = email,
        name = name,
        token = token
)
