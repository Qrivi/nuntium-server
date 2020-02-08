package dev.qrivi.fapp.server.util

import dev.qrivi.fapp.server.constant.SecurityConstants
import dev.qrivi.fapp.server.dto.res.AuthenticatedUser
import dev.qrivi.fapp.server.dto.res.Response
import dev.qrivi.fapp.server.model.Token
import dev.qrivi.fapp.server.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun generateResponse(response: Response): ResponseEntity<Response> {
    return ResponseEntity(response, response.httpHeaders, response.httpStatus)
}

fun generateAccessToken(user: User, token: Token): String {
    val jwt = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.toByteArray()))
            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
            .setSubject(user.email)
            .setExpiration(Date.from(Instant.now().plus(SecurityConstants.TOKEN_TTL, ChronoUnit.HOURS)))
            .claim("refresh_token", token.value)
            .claim("refresh_expiry", token.generated.plus(SecurityConstants.REFRESH_TTL, ChronoUnit.HOURS).epochSecond)
            .compact()
    return SecurityConstants.TOKEN_PREFIX + jwt
}

fun User.toAuthenticatedUser(authorization: String) = AuthenticatedUser(
        authorization = authorization,
        email = email,
        name = name
)

fun User.toNewUser(authorization: String) = AuthenticatedUser(
        httpStatus = HttpStatus.CREATED,
        authorization = authorization,
        email = email,
        name = name
)
