package service.ui

import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration
import java.time.Instant
import java.util.Locale

@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addFormatter(InstantFormatter)
        registry.addFormatter(DurationFormatter)
    }

    object InstantFormatter : Formatter<Instant> {
        override fun print(value: Instant, locale: Locale): String = format(value, locale)

        override fun parse(text: String, locale: Locale): Instant {
            TODO("Not yet implemented")
        }
    }

    object DurationFormatter : Formatter<Duration> {
        override fun print(value: Duration, locale: Locale): String = format(value)

        override fun parse(text: String, locale: Locale): Duration {
            TODO("Not yet implemented")
        }
    }
}
