package dev.qrivi.fapp.server.controller.dto.res.error

import dev.qrivi.fapp.server.controller.dto.res.Response
import org.springframework.http.HttpStatus

data class MethodNotAllowed(
    val error: String = "Request method not supported"
) : Response(httpStatus = HttpStatus.METHOD_NOT_ALLOWED)
