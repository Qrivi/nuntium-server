package dev.qrivi.nuntium.server.controller.dto.res

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountDTO(
    val uuid: String,
    val email: String,
    val name: String?,
    val status: String,
    @JsonProperty("subscription_count") val subscriptionCount: Int

) : Response()
