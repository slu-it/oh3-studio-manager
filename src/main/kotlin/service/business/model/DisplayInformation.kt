package service.business.model

import service.business.model.DisplayInformation.Rented
import service.business.model.DisplayInformation.Rented.OverTime
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.time.Duration
import java.time.Instant

data class DisplayInformation(
    val studioNumber: Int,
    val rented: Rented? = null,
) {
    data class Rented(
        val until: Instant,
        val timeLeft: Duration,
        val overTime: OverTime? = null,
    ) {
        data class OverTime(
            val by: Duration,
            val fee: BigDecimal,
        )
    }
}

fun Studio.toDisplayInformation(now: Instant): DisplayInformation =
    when (this) {
        is AvailableStudio -> displayInformation(this)
        is RentedStudio -> displayInformation(this, now)
    }

private fun displayInformation(studio: AvailableStudio) =
    DisplayInformation(studio.number)

private fun displayInformation(studio: RentedStudio, now: Instant): DisplayInformation {
    val until = studio.rentedUntil

    val isOverTime = now > until
    val timeLeft = if (isOverTime) Duration.ofSeconds(0) else Duration.between(now, until)

    return DisplayInformation(
        studioNumber = studio.number,
        rented = Rented(
            until = until,
            timeLeft = timeLeft,
            overTime = if (isOverTime) overtimeData(until, now, studio) else null
        )
    )
}

private fun overtimeData(until: Instant, now: Instant, studio: RentedStudio): OverTime {
    val overtime = Duration.between(until, now)
    val factor = overtime.dividedBy(studio.overTimeFee.per)
    val feeInEuros = studio.overTimeFee.euros.multiply(BigDecimal(factor))

    return OverTime(by = overtime, fee = toMonetaryAmount(feeInEuros))
}

private fun toMonetaryAmount(feeInEuros: BigDecimal): BigDecimal =
    feeInEuros.setScale(2, HALF_UP)

