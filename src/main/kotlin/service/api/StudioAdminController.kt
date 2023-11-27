package service.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.business.StudioManager
import service.business.model.Studio
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api/admin/studios/{number}")
class StudioAdminController(
    private val manager: StudioManager
) {

    @GetMapping
    fun getStudio(@PathVariable number: Int): ResponseEntity<Studio> =
        manager.get(number).toResponseEntity()

    @PostMapping("/rent")
    fun rentStudio(@PathVariable number: Int, @RequestBody body: BlockRequest): ResponseEntity<Studio> =
        manager.rent(number, body.until.toInstant()).toResponseEntity()

    @PostMapping("/unblock")
    fun unblockStudio(@PathVariable number: Int): ResponseEntity<Studio> =
        manager.unblock(number).toResponseEntity()

    private fun Studio?.toResponseEntity(): ResponseEntity<Studio> =
        this?.let { ok(it) } ?: notFound().build()

    data class BlockRequest(val until: OffsetDateTime)
}
