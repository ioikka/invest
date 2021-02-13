package io.ikka.invest.controller;

import io.ikka.invest.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.exceptions.OpenApiException;
import ru.tinkoff.invest.openapi.models.market.Instrument;
import ru.tinkoff.invest.openapi.models.market.InstrumentsList;
import ru.tinkoff.invest.openapi.models.orders.Order;
import ru.tinkoff.invest.openapi.models.orders.PlacedOrder;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final InstrumentsController instrumentsController;

    @GetMapping(path = "/buy/{ticker}/{lots}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<PlacedOrder>> fetchAndSaveAll(
            @PathVariable("ticker") String ticker,
            @PathVariable("lots") int lots,
            @PathVariable("price") double price) {
        InstrumentsList body = instrumentsController.find(ticker).getBody();

        try {
            if (body != null && body.instruments.size() == 1) {
                Instrument instrument = body.instruments.get(0);
                PlacedOrder placedOrder = ordersService.placeBuyLimitOrder(instrument.figi, lots, BigDecimal.valueOf(price));
                return ResponseEntity.ok(new Response<>("0", null, placedOrder));
            }
        } catch (Exception e) {
            if (e instanceof OpenApiException) {
                return ResponseEntity.ok(new Response<>("1", ((OpenApiException) e).getCode() + " " + e.getMessage(), null));
            }
            log.error("", e);
        }

        throw new IllegalArgumentException();
    }

    @GetMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancelOrder(@PathVariable("id") String orderId) {
        ordersService.cancelOrder(orderId);
        return ResponseEntity.ok(
                Response.builder()
                        .errCode("0")
                        .errDesc(String.format("order %s canceled", orderId))
                        .build()
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> orders() {
        return ResponseEntity.ok(ordersService.getOrders());
    }
}
