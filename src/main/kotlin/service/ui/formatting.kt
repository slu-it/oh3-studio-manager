package service.ui

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.chrono.Chronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.util.Locale

private val germany = ZoneId.of("Europe/Berlin")

fun format(value: Instant, locale: Locale): String {
    val pattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
        FormatStyle.SHORT,
        FormatStyle.MEDIUM,
        Chronology.ofLocale(locale),
        locale
    )
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    val aaa = value.atZone(germany)
    val f = formatter.withLocale(locale)
    return f.format(aaa)
}

fun format(value: Duration) = buildString {
    append("${value.toHoursPart()}".padStart(2, '0'))
    append(':')
    append("${value.toMinutesPart()}".padStart(2, '0'))
    append(':')
    append("${value.toSecondsPart()}".padStart(2, '0'))
}
