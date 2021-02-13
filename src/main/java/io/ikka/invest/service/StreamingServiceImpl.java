package io.ikka.invest.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.StreamingContext;
import ru.tinkoff.invest.openapi.models.market.CandleInterval;
import ru.tinkoff.invest.openapi.models.streaming.StreamingRequest;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@Component
public class StreamingServiceImpl {
    private final StreamingContext streamingContext;

    public void subscribe(String figi) {
        final var listener = new StreamingApiSubscriber(Logger.getLogger(this.getClass().getSimpleName()), Executors.newSingleThreadExecutor());

        streamingContext.sendRequest(StreamingRequest.subscribeCandle(figi, CandleInterval.ONE_MIN));
        streamingContext.sendRequest(StreamingRequest.subscribeInstrumentInfo(figi));
//        streamingContext.sendRequest(StreamingRequest.subscribeOrderbook(figi, 20));
        streamingContext.getEventPublisher().subscribe(listener);
    }

    public void unsubscribe(String figi) {
        log.info("unsubscribe request = ");

        streamingContext.sendRequest(StreamingRequest.unsubscribeCandle(figi, CandleInterval.ONE_MIN));
//        streamingContext.sendRequest(StreamingRequest.unsubscribeOrderbook(figi, 20));
        streamingContext.sendRequest(StreamingRequest.unsubscribeInstrumentInfo(figi));
    }

    public void unsubscribe() {
        log.info("unsubscribe request = ");
    }
}
