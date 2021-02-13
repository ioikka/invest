package io.ikka.invest.service;

import lombok.SneakyThrows;
import ru.tinkoff.invest.openapi.models.orders.LimitOrder;
import ru.tinkoff.invest.openapi.models.orders.Operation;
import ru.tinkoff.invest.openapi.models.orders.Order;
import ru.tinkoff.invest.openapi.models.orders.PlacedOrder;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersService {
    @SneakyThrows
    List<Order> getOrders();

    @SneakyThrows
    void cancelOrder(String orderId);

    @SneakyThrows
    PlacedOrder placeLimitOrder(String figi, LimitOrder limitOrder);

    @SneakyThrows
    PlacedOrder placeLimitOrder(String figi, int lots, Operation operation, BigDecimal price);

    @SneakyThrows
    PlacedOrder placeSellLimitOrder(String figi, int lots, BigDecimal price);

    @SneakyThrows
    PlacedOrder placeBuyLimitOrder(String figi, int lots, BigDecimal price);

    @SneakyThrows
    PlacedOrder placeMarketOrder(String figi, int lots, Operation operation);
}
