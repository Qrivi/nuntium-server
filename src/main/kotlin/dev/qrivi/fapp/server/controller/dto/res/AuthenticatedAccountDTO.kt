package dev.qrivi.fapp.server.controller.dto.res

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.qrivi.fapp.server.constant.SecurityConstants
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

data class AuthenticatedAccountDTO(
    @JsonIgnore override val httpStatus: HttpStatus = HttpStatus.OK,
    @JsonIgnore override val httpHeaders: HttpHeaders = HttpHeaders(),

    @JsonIgnore val authorization: String,

    val email: String,
    val name: String
) : Response() {
    init {
        httpHeaders.add(SecurityConstants.TOKEN_HEADER, this.authorization)
    }
}
