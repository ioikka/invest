package io.ikka.invest.controller;

import io.ikka.invest.dto.ClosedPosition;
import io.ikka.invest.service.OperationsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.models.operations.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/operations")
public class OperationsController {
    private final OperationsService service;

    public static void s() {
        Long l = 0l;

        outer:
        for (int i = 2; i < 1000; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0)
                    continue outer;
            }
            System.out.println(i);
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> operations() {
        return ResponseEntity.ok(service.getOperations().operations);
    }

    @GetMapping(path = "/closedPositions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClosedPosition>> getClosedPositions() {
        List<ClosedPosition> closedPositions = service.getClosedPositions();

        closedPositions.stream().sorted((p1, p2) -> {
            Function<ClosedPosition, BigDecimal> profitFunc = closedPosition -> {
                BigDecimal buyAmount = closedPosition.getBuyAmount();
                BigDecimal sellAmount = closedPosition.getSellAmount();
                BigDecimal brokerFeeAmount = closedPosition.getBrokerFeeAmount();
                if (buyAmount != null && sellAmount != null && brokerFeeAmount != null) {
                    return sellAmount.subtract(buyAmount).subtract(brokerFeeAmount);
                }
                return BigDecimal.ZERO;
            };
            return profitFunc.apply(p1).compareTo(profitFunc.apply(p2));
        }).collect(Collectors.toList()).forEach(r -> {
            BigDecimal buyAmount = r.getBuyAmount();
            BigDecimal sellAmount = r.getSellAmount();
            BigDecimal brokerFeeAmount = r.getBrokerFeeAmount();

            if (buyAmount != null && sellAmount != null && brokerFeeAmount != null) {
                BigDecimal subtract = sellAmount.subtract(buyAmount).subtract(brokerFeeAmount);
                System.out.println(String.format("%s -%s + %s - %s = %s %s ",
                        r.getTicker(),
                        buyAmount,
                        sellAmount,
                        brokerFeeAmount,
                        subtract,
                        percent(sellAmount, buyAmount.add(brokerFeeAmount))));
            }
        });

        return ResponseEntity.ok(closedPositions);
    }

    /**
     * Calculates percentage using formula: (income-expense)*100/expense.
     *
     * @param income  income/sell
     * @param expense expense/buy/
     * @return (income - expense)*100/expense
     */
    public static BigDecimal percent(@NonNull BigDecimal income, @NonNull BigDecimal expense) {
        //(income-expense)*100/expense
        return income
                .subtract(expense)
                .multiply(BigDecimal.valueOf(100L)).setScale(4, RoundingMode.HALF_UP)
                .divide(expense, 4, RoundingMode.HALF_UP);
    }
}
