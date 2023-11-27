package service.business.model

import java.time.Duration

data class OvertimeFees(
    val euros: Double,
    val per: Duration,
) {
    companion object {
        val DEFAULT = OvertimeFees(0.5, Duration.ofMinutes(1))
    }
}
