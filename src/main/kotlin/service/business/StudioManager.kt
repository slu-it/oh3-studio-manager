package service.business

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import service.ApplicationProperties
import service.business.model.AvailableStudio
import service.business.model.OverTimeFee
import service.business.model.RentedStudio
import service.business.model.Studio
import java.time.Instant

@Service
class StudioManager(
    private val properties: ApplicationProperties
) {

    private val log = getLogger(javaClass)
    private val database: MutableMap<Int, Studio> = mutableMapOf()

    @PostConstruct
    fun init() {
        repeat(properties.numberOfStudios) { index ->
            val studioNumber = index + 1
            database[studioNumber] = AvailableStudio(studioNumber)
            log.info("Initialized Studio #$studioNumber")
        }
    }

    fun getAll(): List<Studio> =
        database.values.toList()

    fun get(number: Int): Studio? =
        database[number]

    fun rent(number: Int, until: Instant, overTimeFee: OverTimeFee = OverTimeFee.DEFAULT): Studio? =
        updateStudio(
            number = number,
            studio = RentedStudio(
                number = number,
                rentedUntil = until,
                overTimeFee = overTimeFee
            )
        )

    fun reset(number: Int): Studio? =
        updateStudio(
            number = number,
            studio = AvailableStudio(
                number = number
            )
        )

    private fun updateStudio(number: Int, studio: Studio): Studio? =
        if (database.contains(number)) {
            database[number] = studio
            log.info("Updated Studio #$number: $studio")
            studio
        } else {
            null
        }
}
