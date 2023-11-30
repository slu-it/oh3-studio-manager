package service.ui

import org.springframework.context.i18n.LocaleContextHolder
import service.business.model.AvailableStudio
import service.business.model.RentedStudio
import service.business.model.Studio

data class StudioModel(
    val number: Int,
    val status: String,
)

fun asModel(studio: Studio): StudioModel =
    StudioModel(
        number = studio.number,
        status = when (studio) {
            is AvailableStudio -> "available"
            is RentedStudio -> "rented until ${format(studio.rentedUntil, localeFromContext())}"
        }
    )

private fun localeFromContext() = LocaleContextHolder.getLocale()
