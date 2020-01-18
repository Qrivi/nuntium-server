package dev.qrivi.fapp.server.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id var id: Long,
    @Indexed var email: String,
    var name: String,
    var password: String,
    var feeds: List<Feed>
)
