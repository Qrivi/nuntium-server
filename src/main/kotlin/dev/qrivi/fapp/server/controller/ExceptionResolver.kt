package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.dto.res.Response
import dev.qrivi.fapp.server.dto.res.error.BadRequest
import dev.qrivi.fapp.server.dto.res.error.NotFound
import dev.qrivi.fapp.server.service.MessageService
import dev.qrivi.fapp.server.util.generateResponse
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
}