package dev.qrivi.nuntium.server.controller.dto.res

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.qrivi.nuntium.server.constant.SecurityConstants
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

data class AuthenticationDTO(
    @JsonIgnore override val httpStatus: HttpStatus = HttpStatus.OK,
    @JsonIgnore override val httpHeaders: HttpHeaders = HttpHeaders(),

    @JsonIgnore val authorization: String,

    val email: String,
    val name: String?
) : Response() {
    init {
        httpHeaders.add(SecurityConstants.TOKEN_HEADER, this.authorization)
    }
}
