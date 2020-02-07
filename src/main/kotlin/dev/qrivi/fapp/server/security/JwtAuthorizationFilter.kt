package dev.qrivi.fapp.server.security

import dev.qrivi.fapp.server.constants.SecurityConstants
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class JwtAuthorizationFilter(authenticationManager: AuthenticationManager) : BasicAuthenticationFilter(authenticationManager) {

    private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val auth = this.getAuthentication(req)

        if (auth != null)
            SecurityContextHolder.getContext().authentication = auth

        chain.doFilter(req, res)
    }

    private fun getAuthentication(req: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val authHeader = req.getHeader(SecurityConstants.TOKEN_HEADER) ?: ""

        if (authHeader.isNotEmpty() && authHeader.startsWith(SecurityConstants.TOKEN_PREFIX))
            try {
                val key = SecurityConstants.JWT_SECRET.toByteArray()
                val token = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(authHeader.replace(SecurityConstants.TOKEN_PREFIX, ""))

                val username = token.body.subject
                // val authorities = (token.body["rol"] as List<*>).map { SimpleGrantedAuthority(it as String) }

                if (username.isNotEmpty())
                    return UsernamePasswordAuthenticationToken(username, null, null)
            } catch (e: ExpiredJwtException) {
                log.warn("Request to parse expired JWT : {} failed : {}", authHeader, e.message)
            } catch (e: UnsupportedJwtException) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", authHeader, e.message)
            } catch (e: MalformedJwtException) {
                log.warn("Request to parse invalid JWT : {} failed : {}", authHeader, e.message)
            } catch (e: SignatureException) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", authHeader, e.message)
            } catch (e: IllegalArgumentException) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", authHeader, e.message)
            }
        return null
    }
}
