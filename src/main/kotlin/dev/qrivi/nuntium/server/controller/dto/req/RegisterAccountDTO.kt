package dev.qrivi.nuntium.server.controller.dto.req

import dev.qrivi.nuntium.server.controller.dto.validation.WhitelistedPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegisterAccountDTO(
    @get:NotEmpty(message = "{NotEmpty.RegisterAccountDTO.email}")
    @get:Email(message = "{Email.RegisterAccountDTO.email}")
    val email: String,

    @get:NotEmpty(message = "{NotEmpty.RegisterAccountDTO.password}")
    @get:Size(min = 8, message = "{Size.RegisterAccountDTO.password}")
    @get:WhitelistedPassword(message = "{WhitelistedPassword.RegisterAccountDTO.password}")
    val password: String,

    val name: String?,
    val client: String?
)
