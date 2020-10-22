package dev.qrivi.fapp.server.controller.dto.res

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdatedEntityDTO(
    @JsonProperty("updated") val updatedFields: List<String>,
    @JsonProperty("entity") val updatedEntity: Response

) : Response()
