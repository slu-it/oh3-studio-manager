package service.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import service.business.StudioManager
import service.business.model.toDisplayInformation
import java.time.Clock

@Controller
@RequestMapping("/view")
class ViewController(
    private val manager: StudioManager,
    private val clock: Clock
) {

    @GetMapping("/studio/{number}")
    fun studio(model: Model, @PathVariable number: Int): String {
        val studio = manager.get(number) ?: error("unknown studio: #$number")
        val displayInformation = studio.toDisplayInformation(clock.instant())
        model.addAttribute("language", "en")
        model.addAttribute("info", displayInformation)
        return "view/studio"
    }
}
