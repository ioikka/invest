package io.ikka.invest.service.impl;

import io.ikka.invest.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.PortfolioContext;
import ru.tinkoff.invest.openapi.models.portfolio.Portfolio;
import ru.tinkoff.invest.openapi.models.portfolio.PortfolioCurrencies;

@RequiredArgsConstructor
@Slf4j
@Component
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioContext portfolioContext;

    @SneakyThrows
    @Override
    public Portfolio getPortfolio() {
        return portfolioContext.getPortfolio(null).get();
    }

    @SneakyThrows
    @Override
    public PortfolioCurrencies getPortfolioCurrencies() {
        return portfolioContext.getPortfolioCurrencies(null).get();
    }
}
