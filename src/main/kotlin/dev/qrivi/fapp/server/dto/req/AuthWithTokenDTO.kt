package dev.qrivi.fapp.server.dto.req

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class AuthWithTokenDTO(
        @get:NotEmpty(message = "{NotEmpty.AuthWithTokenDTO.email}")
        @get:Email(message = "{Email.AuthWithTokenDTO.email}")
        val email: String,

        @get:NotEmpty(message = "{NotEmpty.AuthWithTokenDTO.token}")
        @get:Pattern(message = "{Pattern.AuthWithTokenDTO.token}", regexp = "/^[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}$/")
        val token: String
)
