package service.business.model

import java.time.Instant

sealed interface Studio {
    val number: Int
}

data class AvailableStudio(
    override val number: Int,
) : Studio

data class RentedStudio(
    override val number: Int,
    val rentedUntil: Instant,
    val overtimeFees: OvertimeFees,
) : Studio
