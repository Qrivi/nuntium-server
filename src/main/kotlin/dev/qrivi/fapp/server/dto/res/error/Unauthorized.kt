package dev.qrivi.fapp.server.dto.res.error

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.qrivi.fapp.server.dto.res.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

data class Unauthorized(
    @JsonIgnore val reason: Reason,
    @JsonIgnore val realm: String,
    var error: String = "This resource requires valid authentication credentials"
) : Response(
        httpStatus = HttpStatus.UNAUTHORIZED,
        httpHeaders = HttpHeaders()) {
    init {
        var updatedError = true

        when (reason) {
            Reason.INVALID_ACCESS_TOKEN -> this.error = "The provided access token is invalid"
            Reason.INVALID_REFRESH_TOKEN -> this.error = "The provided refresh token is invalid"
            Reason.EXPIRED_ACCESS_TOKEN -> this.error = "The provided access token has expired"
            Reason.EXPIRED_REFRESH_TOKEN -> this.error = "The provided refresh token has expired"
            else -> updatedError = false
        }

        if (updatedError)
            httpHeaders.add(HttpHeaders.WWW_AUTHENTICATE, """Bearer realm="$realm", error="invalid_token", error_description="$error"""")
        else
            httpHeaders.add(HttpHeaders.WWW_AUTHENTICATE, """Bearer realm="$realm"""")
    }

    enum class Reason {
        NO_TOKEN_PROVIDED,
        INVALID_ACCESS_TOKEN,
        INVALID_REFRESH_TOKEN,
        EXPIRED_ACCESS_TOKEN,
        EXPIRED_REFRESH_TOKEN
    }
}
