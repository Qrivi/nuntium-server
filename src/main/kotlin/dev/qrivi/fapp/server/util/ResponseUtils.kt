package dev.qrivi.fapp.server.util

import dev.qrivi.fapp.server.constant.SecurityConstants
import dev.qrivi.fapp.server.controller.dto.res.AuthenticatedAccount
import dev.qrivi.fapp.server.controller.dto.res.Response
import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.persistence.entity.Session
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

fun generateResponse(response: Response): ResponseEntity<Response> {
    return ResponseEntity(response, response.httpHeaders, response.httpStatus)
}

fun generateAccessToken(account: Account, session: Session): String {
    val jwt = Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.toByteArray()))
        .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
        .setIssuer(SecurityConstants.TOKEN_ISSUER)
        .setAudience(SecurityConstants.TOKEN_AUDIENCE)
        .setSubject(account.email)
        .setExpiration(Date.from(Instant.now().plus(SecurityConstants.TOKEN_TTL, ChronoUnit.HOURS)))
        .claim("refresh_token", session.token)
        .claim("refresh_expiry", session.firstActive.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).toEpochSecond())
        .compact()
    return SecurityConstants.TOKEN_PREFIX + jwt
}

fun Account.toAuthenticatedAccount(authorization: String) = AuthenticatedAccount(
    authorization = authorization,
    email = email,
    name = name
)

fun Account.toNewAccount(authorization: String) = AuthenticatedAccount(
    httpStatus = HttpStatus.CREATED,
    authorization = authorization,
    email = email,
    name = name
)
