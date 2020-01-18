package dev.qrivi.fapp.server.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "feeds")
data class Feed(
    @Id var id: String? = null,
    @Indexed var url: String
)
