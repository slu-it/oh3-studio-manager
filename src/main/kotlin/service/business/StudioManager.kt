package service.business

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import service.ApplicationProperties
import service.business.model.AvailableStudio
import service.business.model.OvertimeFees
import service.business.model.RentedStudio
import service.business.model.Studio
import java.time.Instant

@Service
class StudioManager(
    private val properties: ApplicationProperties
) {

    private val database: MutableMap<Int, Studio> = mutableMapOf()

    @PostConstruct
    fun init() {
        repeat(properties.numberOfStudios) { studioNumber ->
            database[studioNumber] = AvailableStudio(studioNumber)
        }
    }

    fun get(number: Int): Studio? =
        database[number]

    fun rent(number: Int, until: Instant): Studio? =
        updateStudio(
            number = number,
            newValue = RentedStudio(
                number = number,
                rentedUntil = until,
                overtimeFees = OvertimeFees.DEFAULT
            )
        )

    fun unblock(number: Int): Studio? =
        updateStudio(
            number = number,
            newValue = AvailableStudio(
                number = number
            )
        )

    private fun updateStudio(number: Int, newValue: Studio): Studio? =
        if (database.contains(number)) {
            database[number] = newValue
            newValue
        } else {
            null
        }
}
