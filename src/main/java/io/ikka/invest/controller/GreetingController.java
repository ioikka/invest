package io.ikka.invest.controller;

import io.ikka.invest.dto.ClosedPosition;
import io.ikka.invest.service.OperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GreetingController {
    private final OperationsService service;

    @GetMapping("/index")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        List<ClosedPosition> closedPositions = service.getClosedPositions();
        model.addAttribute("closedPositions", closedPositions);
        return "index";
    }

}
