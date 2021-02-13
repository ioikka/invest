package io.ikka.invest.controller

import io.ikka.invest.service.OperationsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class KGreetingController(val service: OperationsService) {

    @GetMapping("/cc")
    fun greeting(
        @RequestParam(name = "name", required = false, defaultValue = "World") name: String?,
        model: Model
    ): String {
        model.addAttribute("closedPositions", service.closedPositions)
        return "index"
    }
}
