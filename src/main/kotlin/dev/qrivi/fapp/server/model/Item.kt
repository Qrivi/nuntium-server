package dev.qrivi.fapp.server.model

import java.time.Instant

data class Item(
    var title: String,
    var link: String,
    var guid: String,
    var pubDate: Instant,
    var description: String
// + notes
// + listening position if podcast
)
