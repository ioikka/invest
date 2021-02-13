package io.ikka.invest.service;

import lombok.SneakyThrows;
import ru.tinkoff.invest.openapi.models.portfolio.Portfolio;
import ru.tinkoff.invest.openapi.models.portfolio.PortfolioCurrencies;

public interface PortfolioService {
    Portfolio getPortfolio();

    @SneakyThrows
    PortfolioCurrencies getPortfolioCurrencies();
}
