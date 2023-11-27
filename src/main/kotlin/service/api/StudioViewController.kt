package service.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.business.StudioManager
import service.business.model.DisplayInformation
import service.business.model.toDisplayInformation
import java.time.Clock

@RestController
@RequestMapping("/api/view/studios/{number}")
class StudioViewController(
    private val manager: StudioManager,
    private val clock: Clock
) {

    @GetMapping
    fun getStudio(@PathVariable number: Int): ResponseEntity<DisplayInformation> =
        manager.get(number)?.toDisplayInformation(clock.instant()).toResponseEntity()

    private fun DisplayInformation?.toResponseEntity(): ResponseEntity<DisplayInformation> =
        this?.let { ok(it) } ?: notFound().build()
}
