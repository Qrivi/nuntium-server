package dev.qrivi.fapp.server.dto.req

import dev.qrivi.fapp.server.validation.WhitelistedPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegisterUserDTO(
    @get:NotEmpty(message = "{NotEmpty.RegisterUserDTO.email}")
    @get:Email(message = "{Email.RegisterUserDTO.email}")
    val email: String,

    @get:NotEmpty(message = "{NotEmpty.RegisterUserDTO.password}")
    @get:Size(min = 8, message = "{Size.RegisterUserDTO.password}")
    @get:WhitelistedPassword(message = "{WhitelistedPassword.RegisterUserDTO.password}")
    val password: String,

    val name: String = "Human",
    val agent: String = "Unknown Device"
)
