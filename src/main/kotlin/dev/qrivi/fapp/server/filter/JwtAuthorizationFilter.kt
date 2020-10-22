package dev.qrivi.fapp.server.filter

import dev.qrivi.fapp.server.constant.SecurityConstants
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(authenticationManager: AuthenticationManager, private val handlerExceptionResolver: HandlerExceptionResolver) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        try {
            SecurityContextHolder.getContext().authentication = this.authenticate(req)
            chain.doFilter(req, res)
        } catch (e: Exception) {
            handlerExceptionResolver.resolveException(req, res, null, e)
            return
        }
    }

    // Throws exceptions if authentication fails
    @Throws(
        ExpiredJwtException::class, // when the JWT is expired
        UnsupportedJwtException::class, // when the JWT is signed with a different key/algorithm
        MalformedJwtException::class, // when the JWT is looking weird
        SignatureException::class, // when the JWT signature is invalid
        JwtException::class // when authorization header is missing (wrapped IllegalArgumentException)
    )
    private fun authenticate(req: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val authHeader = req.getHeader(SecurityConstants.TOKEN_HEADER) ?: throw JwtException("Authorization header is missing")
        if (!authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) throw UnsupportedJwtException("Authorization header is not a JWT")

        val token = Jwts.parserBuilder()
            .setSigningKey(SecurityConstants.JWT_SECRET.toByteArray())
            .build()
            .parseClaimsJws(authHeader.replace(SecurityConstants.TOKEN_PREFIX, ""))

        return UsernamePasswordAuthenticationToken(token.body.subject, null, null)
    }
}
