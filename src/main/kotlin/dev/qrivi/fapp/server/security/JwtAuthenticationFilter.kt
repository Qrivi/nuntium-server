package dev.qrivi.fapp.server.security

import dev.qrivi.fapp.server.constants.SecurityConstants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
    init {
        this.authenticationManager = authenticationManager
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL)
    }

    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val username = req.getParameter("username")
        val password = req.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain, auth: Authentication) {
        val user = auth.principal as User
        val roles = user.authorities.map { it.authority }
        val key = SecurityConstants.JWT_SECRET.toByteArray()

        val token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(key))
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.username)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.TOKEN_TTL))
                .claim("rol", roles)
                .compact()
        res.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token)
    }
}
