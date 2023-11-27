package service.business.model

import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.Duration
import java.time.Duration.ofMinutes
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
    val overTimeFee: OverTimeFee,
) : Studio

data class OverTimeFee(
    val euros: BigDecimal,
    val per: Duration,
) {
    companion object {
        val DEFAULT = OverTimeFee(ZERO, ofMinutes(1))
    }
}
