package service

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import service.business.StudioManager
import service.business.model.OverTimeFee
import java.math.BigDecimal
import java.time.Clock
import java.time.Duration.ofMinutes

@Component
@Profile("test")
class TestDataInitializer(
    private val manager: StudioManager,
    private val clock: Clock
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        manager.rent(
            number = 2,
            until = clock.instant().plus(ofMinutes(15))
        )
        manager.rent(
            number = 3,
            until = clock.instant().minus(ofMinutes(16)),
            overTimeFee = OverTimeFee(
                euros = BigDecimal(0.5),
                per = ofMinutes(1)
            )
        )
    }
}
