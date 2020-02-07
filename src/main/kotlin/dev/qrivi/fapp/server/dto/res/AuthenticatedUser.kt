package dev.qrivi.fapp.server.dto.res

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

data class AuthenticatedUser(
    override val httpStatus: HttpStatus = HttpStatus.OK,

    val email: String,
    val name: String,
    @JsonProperty("refresh_token") val token: String
) : Response()
