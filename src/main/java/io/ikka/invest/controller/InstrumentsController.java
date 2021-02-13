package io.ikka.invest.controller;

import io.ikka.invest.dto.Response;
import io.ikka.invest.model.InstrumentEntity;
import io.ikka.invest.service.InstrumentsService;
import io.ikka.invest.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.models.market.Instrument;
import ru.tinkoff.invest.openapi.models.market.InstrumentsList;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/instruments")
public class InstrumentsController {
    private final InstrumentsService instrumentsService;
    private final MarketService marketService;
    private final ModelMapper modelMapper;

    @GetMapping(path = "/fetchAndSaveAll")
    public ResponseEntity<Response<?>> fetchAndSaveAll() {
        List.of(
                marketService.getMarketStocks(),
                marketService.getMarketCurrencies(),
                marketService.getMarketEtfs(),
                marketService.getMarketBonds()
        ).forEach(instrumentsList -> {
            try {
                instrumentsList.instruments.forEach(
                        instrument -> {
                            log.info("saving instrument {}", instrument);
                            System.out.println(instrument);
                            instrumentsService.save(
                                    modelMapper.map(instrument, InstrumentEntity.class));
                        });
            } catch (Exception e) {
                log.error("", e);
            }
        });
        return ResponseEntity.ok(new Response<>());
    }

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstrumentEntity>> find() {
        return ResponseEntity.ok(instrumentsService.getAll());
    }


    @Cacheable(value = "instruments")
    @GetMapping(path = "/searchByTicker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstrumentsList> find(@RequestParam(name = "ticker") String ticker) {
        Assert.hasText(ticker, "[Assertion failed] - ticker argument must have text; it must not be null, empty, or blank");

        log.info("Searching by ticker in repository...");
        List<Instrument> instruments = instrumentsService.findByTicker(ticker).stream()
                .map(it -> new Instrument(
                        it.getFigi(),
                        it.getTicker(),
                        it.isin,
                        it.minPriceIncrement,
                        it.lot,
                        it.currency,
                        it.name,
                        it.type
                ))
                .collect(Collectors.toList());
        log.info("Finished searching by ticker in repository.");

        InstrumentsList body;
        if (!instruments.isEmpty()) {
            body = new InstrumentsList(instruments.size(), instruments);
        } else {
            log.info("Searching by ticker ...");
            body = marketService.searchMarketInstrumentsByTicker(ticker);
            log.info("Finished searching by ticker.");
        }
        return ResponseEntity.ok(body);
    }


    @Cacheable(value = "instruments")
    @GetMapping(path = "/searchByTicker2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instrument> find2(@RequestParam(name = "ticker") String ticker) {
        Assert.hasText(ticker, "[Assertion failed] - ticker argument must have text; it must not be null, empty, or blank");

        log.info("Searching by ticker in repository...");
        List<Instrument> instruments = instrumentsService.findByTicker(ticker).stream()
                .map(it -> new Instrument(
                        it.getFigi(),
                        it.getTicker(),
                        it.isin,
                        it.minPriceIncrement,
                        it.lot,
                        it.currency,
                        it.name,
                        it.type
                ))
                .collect(Collectors.toList());
        log.info("Finished searching by ticker in repository.");

        InstrumentsList body;
        if (!instruments.isEmpty()) {
            body = new InstrumentsList(instruments.size(), instruments);
        } else {
            log.info("Searching by ticker ...");
            body = marketService.searchMarketInstrumentsByTicker(ticker);
            log.info("Finished searching by ticker.");
        }
        return ResponseEntity.ok(body.instruments.get(0));
    }
}
