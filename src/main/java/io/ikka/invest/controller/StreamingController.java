package io.ikka.invest.controller;

import io.ikka.invest.model.InstrumentEntity;
import io.ikka.invest.service.InstrumentsService;
import io.ikka.invest.service.StreamingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/streaming")
public class StreamingController {
    private final StreamingServiceImpl streamingService;
    private final InstrumentsService instrumentsService;

    @GetMapping(path = "/subscribe/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> find(@PathVariable("ticker") String ticker) {

        List<InstrumentEntity> instruments = instrumentsService.findByTicker(ticker);
        if (instruments.size() == 1) {
            streamingService.subscribe(instruments.get(0).getFigi());
        }
        return ResponseEntity.ok(Response.builder().errCode("0").build());
    }

    @GetMapping(path = "/unsubscribe/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> unsubscribe(@PathVariable("ticker") String ticker) {

        List<InstrumentEntity> instruments = instrumentsService.findByTicker(ticker);
        if (instruments.size() == 1) {
            streamingService.unsubscribe(instruments.get(0).getFigi());
        }
        return ResponseEntity.ok(Response.builder().errCode("0").build());
    }

    @GetMapping(path = "/unsubscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> unsubscribe() {

        streamingService.unsubscribe();
        return ResponseEntity.ok(Response.builder().errCode("0").build());
    }


}
