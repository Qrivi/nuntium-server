package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.dto.res.Response
import dev.qrivi.fapp.server.dto.res.error.BadRequest
import dev.qrivi.fapp.server.dto.res.error.NotFound
import dev.qrivi.fapp.server.dto.res.error.Unauthorized
import dev.qrivi.fapp.server.service.MessageService
import dev.qrivi.fapp.server.util.generateResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class ExceptionResolver(private val messages: MessageService) {

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Response> {
        return generateResponse(BadRequest(error = messages["fapp.exception.HttpMessageNotReadableException.message"]))
    }

    @ExceptionHandler
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ResponseEntity<Response> {
        return generateResponse(NotFound(error = messages["fapp.exception.NoHandlerFoundException.message"]))
    }

    @ExceptionHandler
    fun handleExpiredJwtException(e: ExpiredJwtException, req: HttpServletRequest): ResponseEntity<Response> {
        return generateResponse(Unauthorized(reason = Unauthorized.Reason.EXPIRED_ACCESS_TOKEN, realm = req.serverName))
    }

    @ExceptionHandler
    fun handleUnsupportedJwtException(e: UnsupportedJwtException, req: HttpServletRequest): ResponseEntity<Response> {
        return generateResponse(Unauthorized(reason = Unauthorized.Reason.INVALID_ACCESS_TOKEN, realm = req.serverName))
    }

    @ExceptionHandler
    fun handleMalformedJwtException(e: MalformedJwtException, req: HttpServletRequest): ResponseEntity<Response> {
        return generateResponse(Unauthorized(reason = Unauthorized.Reason.INVALID_ACCESS_TOKEN, realm = req.serverName))
    }

    @ExceptionHandler
    fun handleSignatureException(e: SignatureException, req: HttpServletRequest): ResponseEntity<Response> {
        return generateResponse(Unauthorized(reason = Unauthorized.Reason.INVALID_ACCESS_TOKEN, realm = req.serverName))
    }

    @ExceptionHandler
    fun handleJwtException(e: JwtException, req: HttpServletRequest): ResponseEntity<Response> {
        return generateResponse(Unauthorized(reason = Unauthorized.Reason.NO_TOKEN_PROVIDED, realm = req.serverName))
    }
}
