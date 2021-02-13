package io.ikka.invest.controller;

import io.ikka.invest.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.models.portfolio.Portfolio;
import ru.tinkoff.invest.openapi.models.portfolio.PortfolioCurrencies;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Portfolio> portfolio() {
        Portfolio portfolio = portfolioService.getPortfolio();
        return ResponseEntity.ok(portfolio);
    }


    @GetMapping(path = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortfolioCurrencies> portfolioCurrencies() {
        return ResponseEntity.ok(portfolioService.getPortfolioCurrencies());
    }
}
