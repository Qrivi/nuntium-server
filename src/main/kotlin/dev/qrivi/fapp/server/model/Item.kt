package dev.qrivi.fapp.server.model

import java.time.ZonedDateTime

data class Item(
    var title: String,
    var link: String,
    var guid: String,
    var pubDate: ZonedDateTime,
    var description: String
// + notes
// + listening position if podcast
)
