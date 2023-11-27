package service.config.webmvc

import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.chrono.Chronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder.getLocalizedDateTimePattern
import java.time.format.FormatStyle.LONG
import java.util.Locale

@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addFormatter(InstantFormatter)
        registry.addFormatter(DurationFormatter)
    }

    object InstantFormatter : Formatter<Instant> {
        private val germany = ZoneId.of("Europe/Berlin")

        override fun print(value: Instant, locale: Locale): String {
            val pattern = getLocalizedDateTimePattern(LONG, LONG, Chronology.ofLocale(locale), locale)
            val formatter = DateTimeFormatter.ofPattern(pattern, locale)
            val aaa = value.atZone(germany)
            val f = formatter.withLocale(locale)
            return f.format(aaa)
        }

        override fun parse(text: String, locale: Locale): Instant {
            TODO("Not yet implemented")
        }
    }

    object DurationFormatter : Formatter<Duration> {
        override fun print(value: Duration, locale: Locale): String =
            buildString {
                append("${value.toHoursPart()}".padStart(2, '0'))
                append(':')
                append("${value.toMinutesPart()}".padStart(2, '0'))
                append(':')
                append("${value.toSecondsPart()}".padStart(2, '0'))
            }

        override fun parse(text: String, locale: Locale): Duration {
            TODO("Not yet implemented")
        }
    }
}
