package dev.qrivi.fapp.server.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id var id: String? = null,
    var email: String,
    var name: String,
    var password: String,
    var tokens: MutableList<Token> = mutableListOf(),
    val feeds: MutableList<Feed> = mutableListOf()
)
