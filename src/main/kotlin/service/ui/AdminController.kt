package service.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import service.business.StudioManager
import java.time.Clock

@Controller
@RequestMapping("/admin")
class AdminController(
    private val manager: StudioManager,
    private val clock: Clock
) {

    @GetMapping
    fun index(model: Model): String {
        val studios = manager.getAll().map(::asModel)
        model.addAttribute("language", "en")
        model.addAttribute("studios", studios)
        return "admin/index"
    }

    @GetMapping("/{number}")
    fun studio(model: Model, @PathVariable number: Int): String {
        val studio = manager.get(number)?.let(::asModel) ?: error("unknown studio: #$number")
        model.addAttribute("language", "en")
        model.addAttribute("studio", studio)
        return "admin/studio"
    }
}
