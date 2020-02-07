package dev.qrivi.fapp.server.dto.req

import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

data class AuthWithTokenDTO(
    @get:Email(message = "{Email.AuthRefreshDTO.email}")
    val email: String,

    @get:Pattern(message = "{Pattern.AuthRefreshDTO.token}", regexp = "/^[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}$/")
    val token: String
)
