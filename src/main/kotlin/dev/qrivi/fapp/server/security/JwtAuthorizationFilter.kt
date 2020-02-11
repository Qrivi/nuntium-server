package dev.qrivi.fapp.server.security

import dev.qrivi.fapp.server.constant.SecurityConstants
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(authenticationManager: AuthenticationManager, private val handlerExceptionResolver: HandlerExceptionResolver) : BasicAuthenticationFilter(authenticationManager) {

    private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

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
    // - ExpiredJwtException     : when the JWT is expired
    // - UnsupportedJwtException : when the JWT is signed with a different key/algorithm
    // - MalformedJwtException   : when the JWT is looking weird
    // - SignatureException      : when the JWT's signature is invalid
    // - JwtException            : when authorization header is missing (wrapped IllegalArgumentException)
    private fun authenticate(req: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val authHeader = req.getHeader(SecurityConstants.TOKEN_HEADER) ?: throw JwtException("Authorization header is missing")
        if (!authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) throw UnsupportedJwtException("Authorization header is not a JWT")

        val token = Jwts.parserBuilder()
            .setSigningKey(SecurityConstants.JWT_SECRET.toByteArray())
            .build()
            .parseClaimsJws(authHeader.replace(SecurityConstants.TOKEN_PREFIX, ""))

        return UsernamePasswordAuthenticationToken(token.body.subject, null, null)
    }
}
