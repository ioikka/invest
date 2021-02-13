package io.ikka.invest.service.impl;

import io.ikka.invest.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.OrdersContext;
import ru.tinkoff.invest.openapi.models.orders.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrdersServiceImpl implements OrdersService {
    public static final String BROKER_ACCOUNT_ID = "2008308542";
    private final OrdersContext ordersContext;

    @SneakyThrows
    @Override
    public List<Order> getOrders() {
        return ordersContext.getOrders(BROKER_ACCOUNT_ID).get();
    }


    @SneakyThrows
    @Override
    public void cancelOrder(String orderId) {
        ordersContext.cancelOrder(orderId, BROKER_ACCOUNT_ID).get();
    }

    @SneakyThrows
    @Override
    public PlacedOrder placeLimitOrder(String figi, LimitOrder limitOrder) {
        return ordersContext.placeLimitOrder(figi, limitOrder, BROKER_ACCOUNT_ID).get();
    }

    @SneakyThrows
    @Override
    public PlacedOrder placeLimitOrder(String figi, int lots, Operation operation, BigDecimal price) {
        return placeLimitOrder(figi, new LimitOrder(lots, operation, price));
    }

    @SneakyThrows
    @Override
    public PlacedOrder placeSellLimitOrder(String figi, int lots, BigDecimal price) {
        return placeLimitOrder(figi, lots, Operation.Sell, price);
    }

    @SneakyThrows
    @Override
    public PlacedOrder placeBuyLimitOrder(String figi, int lots, BigDecimal price) {
        return placeLimitOrder(figi, lots, Operation.Buy, price);
    }

    @SneakyThrows
    @Override
    public PlacedOrder placeMarketOrder(String figi, int lots, Operation operation) {
        return ordersContext.placeMarketOrder(figi, new MarketOrder(lots, operation), BROKER_ACCOUNT_ID).get();
    }
}
