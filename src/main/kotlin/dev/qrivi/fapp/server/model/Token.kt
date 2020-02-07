package dev.qrivi.fapp.server.model

import java.time.LocalDateTime
import java.util.UUID

data class Token(
    val description: String
) {
    val token = UUID.randomUUID().toString()
    val generated = LocalDateTime.now()
}
