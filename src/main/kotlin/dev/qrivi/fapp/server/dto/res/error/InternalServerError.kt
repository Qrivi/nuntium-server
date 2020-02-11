package dev.qrivi.fapp.server.dto.res.error

import dev.qrivi.fapp.server.dto.res.Response
import org.springframework.http.HttpStatus

data class InternalServerError(
    val errors: List<String>
) : Response(httpStatus = HttpStatus.INTERNAL_SERVER_ERROR) {
    constructor(error: String) : this(listOf(error))
}
