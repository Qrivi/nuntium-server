package dev.qrivi.fapp.server.dto.req

import dev.qrivi.fapp.server.validation.WhitelistedPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegisterUserDTO(
    @get:NotEmpty(message = "{NotEmpty.AuthWithPasswordDTO.email}")
    @get:Email(message = "{Email.AuthWithPasswordDTO.email}")
    val email: String,

    @get:NotEmpty(message = "{NotEmpty.AuthWithPasswordDTO.password}")
    @get:Size(min = 8, message = "{Size.AuthWithPasswordDTO.password}")
    @get:WhitelistedPassword(message = "{WhitelistedPassword.AuthWithPasswordDTO.password}")
    val password: String,

    val name: String = "Human",
    val agent: String = "Unknown Device"
)
