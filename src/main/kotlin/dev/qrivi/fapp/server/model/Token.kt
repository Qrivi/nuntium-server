package dev.qrivi.fapp.server.model

import java.time.Instant
import java.util.UUID

data class Token(
    val description: String
) {
    var value: String = UUID.randomUUID().toString()
    var generated: Instant = Instant.now()
}
