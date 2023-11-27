package service.business.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds
import java.time.Instant.parse

class DisplayInformationTests {

    @Test
    fun `available studios are converted correctly`() {
        val now = parse("2023-11-27T12:34:56.789Z")
        val studio = AvailableStudio(number = 1)
        val displayInformation = studio.toDisplayInformation(now)

        assertThat(displayInformation).isEqualTo(
            DisplayInformation(
                number = 1,
                rented = null,
                overtime = null
            )
        )
    }

    @Nested
    @DisplayName("rented studios are converted correctly")
    inner class RentedStudios {

        @Test
        fun `in time case is calculated correctly`() {
            val now = parse("2023-11-27T12:34:56.789Z")
            val until = parse("2023-11-27T13:00:00.000Z")
            val fees = OvertimeFees(0.5, ofMinutes(1))

            val studio = RentedStudio(number = 2, rentedUntil = until, overtimeFees = fees)
            val displayInformation = studio.toDisplayInformation(now)

            assertThat(displayInformation).isEqualTo(
                DisplayInformation(
                    number = 2,
                    rented = RentingData(
                        until = until,
                        timeLeft = ofMinutes(25).plusSeconds(3).plusMillis(211)
                    ),
                    overtime = null
                )
            )
        }

        @Test
        fun `over time case is calculated correctly`() {
            val now = parse("2023-11-27T12:34:56.789Z")
            val until = parse("2023-11-27T12:15:00.000Z")
            val fees = OvertimeFees(0.5, ofMinutes(1))

            val studio = RentedStudio(number = 3, rentedUntil = until, overtimeFees = fees)
            val displayInformation = studio.toDisplayInformation(now)

            assertThat(displayInformation).isEqualTo(
                DisplayInformation(
                    number = 3,
                    rented = RentingData(
                        until = until,
                        timeLeft = ofSeconds(0)
                    ),
                    overtime = OvertimeData(
                        amount = ofMinutes(19).plusSeconds(56).plusMillis(789),
                        fee = BigDecimal("9.50")
                    )
                )
            )
        }
    }
}
