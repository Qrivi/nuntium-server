package dev.qrivi.fapp.server.model

import java.time.ZonedDateTime
import java.util.UUID

data class Token(
    val description: String
) {
    val value: String = UUID.randomUUID().toString()
    var generated: ZonedDateTime = ZonedDateTime.now()
}
