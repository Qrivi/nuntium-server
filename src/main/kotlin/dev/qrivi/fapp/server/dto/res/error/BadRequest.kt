package dev.qrivi.fapp.server.dto.res.error

import dev.qrivi.fapp.server.dto.res.Response
import org.springframework.http.HttpStatus

data class BadRequest(
    val errors: List<String>
) : Response(httpStatus = HttpStatus.BAD_REQUEST) {
    constructor(error: String) : this(listOf(error))
}
