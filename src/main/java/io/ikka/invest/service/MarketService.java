package io.ikka.invest.service;

import lombok.SneakyThrows;
import ru.tinkoff.invest.openapi.models.market.Instrument;
import ru.tinkoff.invest.openapi.models.market.InstrumentsList;

import java.util.Optional;

public interface MarketService {

    @SneakyThrows
    InstrumentsList getMarketStocks();

    @SneakyThrows
    InstrumentsList getMarketCurrencies();

    @SneakyThrows
    InstrumentsList getMarketBonds();

    @SneakyThrows
    InstrumentsList getMarketEtfs();

    @SneakyThrows
    Optional<Instrument> searchMarketInstrumentByFigi(String figi);

    @SneakyThrows
    InstrumentsList searchMarketInstrumentsByTicker(String ticker);
}
