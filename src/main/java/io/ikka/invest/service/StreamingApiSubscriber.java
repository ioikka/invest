package io.ikka.invest.service;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.invest.openapi.models.streaming.StreamingEvent;

import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class StreamingApiSubscriber extends org.reactivestreams.example.unicast.AsyncSubscriber<StreamingEvent> {

    private final Logger logger;

    StreamingApiSubscriber(@NotNull final Logger logger, @NotNull final Executor executor) {
        super(executor);
        this.logger = logger;
    }

    @Override
    protected boolean whenNext(final StreamingEvent event) {
        logger.info("Пришло новое событие из Streaming API\n" + event);

        return true;
    }

}
