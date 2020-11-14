package dev.qrivi.nuntium.server.controller

import dev.qrivi.nuntium.server.controller.dto.res.InfoDTO
import dev.qrivi.nuntium.server.controller.dto.res.Response
import dev.qrivi.nuntium.server.util.generateResponse
import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
@RequestMapping("/info")
class InfoController(
    private val buildProperties: BuildProperties
) {

    @GetMapping
    fun getInfo(): ResponseEntity<Response> {
        return generateResponse(
            InfoDTO(
                name = "nuntium-${buildProperties.name}",
                version = buildProperties.version,
                build = buildProperties.time,
                clock = ZonedDateTime.now()
            )
        )
    }
}
