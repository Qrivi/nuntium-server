package dev.qrivi.fapp.server.dto.res

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

abstract class Response(
    @JsonIgnore open val httpStatus: HttpStatus = HttpStatus.NOT_IMPLEMENTED,
    @JsonIgnore open val httpHeaders: HttpHeaders = HttpHeaders.EMPTY
)
