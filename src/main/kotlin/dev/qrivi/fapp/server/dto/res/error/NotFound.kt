package dev.qrivi.fapp.server.dto.res.error

import dev.qrivi.fapp.server.dto.res.Response
import org.springframework.http.HttpStatus

data class NotFound(
    val error: String = "The data you are looking for could not be found."
) : Response(httpStatus = HttpStatus.NOT_FOUND)
