package io.ikka.invest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.config.ObjectMapperConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest(classes = {
        ObjectMapperConfig.class,
})
class TinkoffEventsTest {
    @Autowired
    ObjectMapper objectMapper;

    Predicate<TinkoffEvents.Operation> pOperationIsBuy = o -> OperationType.BUY.equals(o.getOperationType());
    Predicate<TinkoffEvents.Operation> pOperationIsSell = o -> o.getOperationType().equals(OperationType.SELL);
    Predicate<TinkoffEvents.Operation> pOperationIsSellOrBy = pOperationIsSell.or(pOperationIsBuy);

    Predicate<TinkoffEvents.Operation> pOperationIsMoneyDeposit = o -> OperationType.MONEY_DEPOSIT.equals(o.getOperationType());
    Predicate<TinkoffEvents.Operation> pOperationIsMoneyWithdrawal = o -> OperationType.MONEY_WITHDRAWAL.equals(o.getOperationType());
    Predicate<TinkoffEvents.Operation> pOperationIsBuyWithCard = o -> OperationType.BUY_WITH_CARD.equals(o.getOperationType());
    Predicate<TinkoffEvents.Operation> pOperationIsBrokFee = o -> o.getOperationType().equals(OperationType.BROKER_FEE);

    Predicate<TinkoffEvents.Operation> pInstrumentIsFx = o -> InstrumentType.FX.equals(o.getInstrumentType());
    Predicate<TinkoffEvents.Operation> pInstrumentIsStock = o -> InstrumentType.STOCK.equals(o.getInstrumentType());
    Predicate<TinkoffEvents.Operation> pTickerIsUSDRUB = o -> "USDRUB".equals(o.getTicker());

    Predicate<TinkoffEvents.Operation> pStatusIsDone = r -> StatusType.DONE.equals(r.getStatus());


    @SneakyThrows
    @Test
    void deserialize() {
        InputStream resource = new ClassPathResource("mocks/001_tinkoffEvents.json").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            final TinkoffEvents tinkoffEvents = objectMapper.readValue(
                    reader.lines().collect(Collectors.joining("\n")),
                    TinkoffEvents.class);


            ZonedDateTime dateAfterFilter = ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault());
            ZonedDateTime dateBeforeFilter = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());

            Predicate<TinkoffEvents.Operation> pDateIsAfterFilter = o -> o.getDate().isAfter(dateAfterFilter);
            Predicate<TinkoffEvents.Operation> pDateIsBeforeFilter = o -> o.getDate().isBefore(dateBeforeFilter);

            Arrays.stream(Currency.values()).forEach(currency -> {
                log.info("Deposited {} {}", currency.name(), sum(
                        tinkoffEvents,
                        o -> currency.equals(o.getCurrency()),
                        pStatusIsDone,
                        pOperationIsMoneyDeposit,
                        pDateIsAfterFilter,
                        pDateIsBeforeFilter));
            });
            Arrays.stream(Currency.values()).forEach(currency -> {
                log.info("Withdrew {} {}", currency.name(), sum(
                        tinkoffEvents,
                        o -> currency.equals(o.getCurrency()),
                        pStatusIsDone,
                        pOperationIsMoneyWithdrawal,
                        pDateIsAfterFilter,
                        pDateIsBeforeFilter));
            });

            Supplier<Stream<TinkoffEvents.Operation>> doneEventsStreamSup = () ->
                    Optional.ofNullable(tinkoffEvents)
                            .orElse(new TinkoffEvents())
                            .getPayload()
                            .getItems()
                            .stream()
                            .filter(pStatusIsDone)
                            .filter(pDateIsAfterFilter);

            List<TinkoffEvents.Operation> collect = doneEventsStreamSup.get()
                    .filter(pOperationIsBuy.or(pOperationIsBuyWithCard))
                    .filter(pInstrumentIsFx)
                    .filter(pTickerIsUSDRUB)
                    .collect(Collectors.toList());

            Map<String, List<TinkoffEvents.Operation>> stocks = doneEventsStreamSup.get()
                    .filter(pInstrumentIsStock)
                    .collect(Collectors.groupingBy(TinkoffEvents.Operation::getIsin));


            Map<Currency, BigDecimal> totalProfits = new HashMap<>(Map.of(
                    Currency.EUR, BigDecimal.ZERO,
                    Currency.USD, BigDecimal.ZERO,
                    Currency.RUB, BigDecimal.ZERO
            ));

            //commissions paid
            commissionsPaid(doneEventsStreamSup);


            final BigDecimal[] totalCommissionInRub = {BigDecimal.ZERO};
            boughtSoldProfitChange(stocks, totalCommissionInRub, totalProfits, pOperationIsSellOrBy);

            totalProfits.forEach((currency, bigDecimal) -> log.info("Profit in {} {}", currency, bigDecimal));
            log.info("commission in RUB {}", totalCommissionInRub[0]);


            LongSummaryStatistics totalUsd = collect.stream()
                    .map(TinkoffEvents.Operation::getQuantity)
                    .collect(Collectors.summarizingLong(Long::longValue));

            log.info("{}", totalUsd.toString());


        }
    }

    private void commissionsPaid(Supplier<Stream<TinkoffEvents.Operation>> doneEventsStreamSup) {
        printNewline(2);
        doneEventsStreamSup.get()
                .filter(pOperationIsBrokFee)
                .collect(Collectors.groupingBy(
                        TinkoffEvents.Operation::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO, TinkoffEvents.Operation::getPayment, BigDecimal::add)))
                .forEach((key, value) -> log.info("{} fee: {}", key, value));
        printNewline(2);
    }

    private void boughtSoldProfitChange(Map<String, List<TinkoffEvents.Operation>> stocks, BigDecimal[] totalCommissionInRub, Map<Currency, BigDecimal> totalProfits, Predicate<TinkoffEvents.Operation> pSellOrBuy) {
        stocks.forEach((isin, operations) -> {
            totalCommissionInRub[0] = totalCommissionInRub[0].add(operations.stream()
                    .filter(pSellOrBuy)
                    .map(TinkoffEvents.Operation::getCommissionRub)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            BigDecimal buySum = operations.stream()
                    .filter(o -> o.getOperationType().equals(OperationType.BUY))
                    .map(TinkoffEvents.Operation::getPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sellSum = operations.stream()
                    .filter(o -> o.getOperationType().equals(OperationType.SELL))
                    .map(TinkoffEvents.Operation::getPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            TinkoffEvents.Operation operation = operations.get(0);
            BigDecimal sellMinusBuy = sellSum.abs().subtract(buySum.abs());
            BigDecimal percent = sellSum.abs().equals(BigDecimal.ZERO)
                    ? BigDecimal.ZERO
                    : sellMinusBuy.divide(buySum.abs(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal profit = sellSum.abs().equals(BigDecimal.ZERO)
                    ? BigDecimal.ZERO
                    : sellMinusBuy;

            totalProfits.put(operation.getCurrency(), totalProfits.get(operation.getCurrency()).add(profit));


            log.info("{} {} B {} {} S {} {}  profit {}, change {}%",
                    isin,
                    operation.getShowName(),
                    buySum.abs(),
                    operation.getCurrency().name(),
                    sellSum,
                    operation.getCurrency().name(),
                    profit,
                    percent);
        });
    }

    private BigDecimal sum(final TinkoffEvents tinkoffEvents,
                           final Predicate<TinkoffEvents.Operation> currencyFilter,
                           final Predicate<TinkoffEvents.Operation> statusFilter,
                           final Predicate<TinkoffEvents.Operation> operationFilter,
                           final Predicate<TinkoffEvents.Operation> startDateFilter,
                           final Predicate<TinkoffEvents.Operation> endDateFilter) {
        return Optional.ofNullable(tinkoffEvents)
                .orElse(new TinkoffEvents())
                .getPayload()
                .getItems()
                .stream()
                .filter(Optional.ofNullable(startDateFilter).orElse(r -> true))
                .filter(Optional.ofNullable(endDateFilter).orElse(r -> true))
                .filter(operationFilter)
                .filter(currencyFilter)
                .filter(statusFilter)
                .map(TinkoffEvents.Operation::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void printNewline(int linesNumber) {
        for (int i = 0; i < linesNumber; i++) {
            log.info("--------------------------------");
        }
    }
}
