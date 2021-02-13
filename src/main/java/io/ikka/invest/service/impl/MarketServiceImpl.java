package io.ikka.invest.service.impl;

import io.ikka.invest.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.models.market.Instrument;
import ru.tinkoff.invest.openapi.models.market.InstrumentsList;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class MarketServiceImpl implements MarketService {
    private final MarketContext marketContext;

    @SneakyThrows
    @Override
    public InstrumentsList getMarketStocks() {
        return marketContext.getMarketStocks().get();
    }

    @SneakyThrows
    @Override
    public InstrumentsList getMarketCurrencies() {
        return marketContext.getMarketCurrencies().get();
    }

    @SneakyThrows
    @Override
    public InstrumentsList getMarketBonds() {
        return marketContext.getMarketBonds().get();
    }

    @SneakyThrows
    @Override
    public InstrumentsList getMarketEtfs() {
        return marketContext.getMarketEtfs().get();
    }

    @SneakyThrows
    @Override
    public Optional<Instrument> searchMarketInstrumentByFigi(String figi) {
        return marketContext.searchMarketInstrumentByFigi(figi).get();
    }

    @Override
    @SneakyThrows
    public InstrumentsList searchMarketInstrumentsByTicker(String ticker) {
        return marketContext.searchMarketInstrumentsByTicker(ticker).get();
    }
}
