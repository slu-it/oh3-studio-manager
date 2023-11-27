package service.business.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.Instant

data class DisplayInformation(
    val number: Int,
    val rented: RentingData? = null,
    val overtime: OvertimeData? = null,
)

data class RentingData(
    val until: Instant,
    val timeLeft: Duration,
)

data class OvertimeData(
    val amount: Duration,
    val fee: BigDecimal,
)

fun Studio.toDisplayInformation(now: Instant): DisplayInformation =
    when (this) {
        is AvailableStudio -> displayInformation(this)
        is RentedStudio -> displayInformation(this, now)
    }

private fun displayInformation(studio: AvailableStudio) =
    DisplayInformation(studio.number)

private fun displayInformation(studio: RentedStudio, now: Instant): DisplayInformation {
    val until = studio.rentedUntil

    val isOvertime = now > until
    val timeLeft = if (isOvertime) Duration.ofSeconds(0) else Duration.between(now, until)

    return DisplayInformation(
        number = studio.number,
        rented = RentingData(until, timeLeft),
        overtime = if (isOvertime) overtimeData(until, now, studio) else null
    )
}

private fun overtimeData(until: Instant, now: Instant, studio: RentedStudio): OvertimeData {
    val overtime = Duration.between(until, now)
    val factor = overtime.dividedBy(studio.overtimeFees.per)
    val feeInEuros = studio.overtimeFees.euros * factor
    return OvertimeData(
        amount = overtime,
        fee = currencyAmount(feeInEuros)
    )
}

private fun currencyAmount(rawFee: Double): BigDecimal =
    BigDecimal(rawFee).setScale(2, RoundingMode.HALF_UP)
