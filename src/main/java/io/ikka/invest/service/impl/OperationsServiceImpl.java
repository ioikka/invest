package io.ikka.invest.service.impl;

import io.ikka.invest.dto.ClosedPosition;
import io.ikka.invest.service.InstrumentsService;
import io.ikka.invest.service.OperationsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.OperationsContext;
import ru.tinkoff.invest.openapi.models.Currency;
import ru.tinkoff.invest.openapi.models.operations.Operation;
import ru.tinkoff.invest.openapi.models.operations.OperationStatus;
import ru.tinkoff.invest.openapi.models.operations.OperationType;
import ru.tinkoff.invest.openapi.models.operations.OperationsList;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OperationsServiceImpl implements OperationsService {
    private final OperationsContext operationsContext;
    private final InstrumentsService instrumentsService;

    @Override
    @SneakyThrows
    public OperationsList getOperations() {
        return operationsContext.getOperations(
                OffsetDateTime.now().minusYears(1),
                OffsetDateTime.now(),
                null,
                null)
                .get();
    }

    @Override
    public List<ClosedPosition> getClosedPositions() {
        List<Operation> operations = getOperations().operations;

        var figiOperationTypeOperationListMap = getOperationsGroupedByFigiAndOperationStatusEquals(operations, OperationStatus.Done);


        Map<String, Map<OperationType, BigDecimal>> figiOperationTypeSumMap = new HashMap<>();
        figiOperationTypeOperationListMap
                .forEach((figi, operationTypeOperationListMap) -> operationTypeOperationListMap
                        .forEach((operationType, operationList) -> {
                            figiOperationTypeSumMap.computeIfAbsent(figi, k -> new HashMap<>());

                            BigDecimal sum = operationList.stream()
                                    .filter(r -> r.operationType != null && r.operationType.equals(operationType))
                                    .map(o -> o.payment)
                                    .reduce(BigDecimal.ZERO, (bigDecimal, bigDecimal2) -> bigDecimal.abs().add(bigDecimal2.abs()));

                            figiOperationTypeSumMap.get(figi).put(operationType, sum);
                        }));

        Map<String, Map<OperationType, BigDecimal>> operationAmountsGroupedByTickerAndOperationType = new HashMap<>();
        figiOperationTypeSumMap.forEach((figi, operationTypeBigDecimalMap) ->
                operationAmountsGroupedByTickerAndOperationType.computeIfAbsent(findTickerByFigi(figi), s -> operationTypeBigDecimalMap));

        List<ClosedPosition> closedPositionList = new ArrayList<>();
        operationAmountsGroupedByTickerAndOperationType.keySet().forEach(ticker -> {
            Map<OperationType, BigDecimal> amountMap = operationAmountsGroupedByTickerAndOperationType.get(ticker);
            closedPositionList.add(
                    ClosedPosition.builder()
                            .buyAmount(amountMap.get(OperationType.Buy))
                            .sellAmount(amountMap.get(OperationType.Sell))
                            .ticker(ticker)
                            .brokerFeeAmount(amountMap.get(OperationType.BrokerCommission))
                            .build());
        });
        return closedPositionList;
    }

    private static final ConcurrentHashMap<String, String> figiToTickerMap = new ConcurrentHashMap<>();

    public String findTickerByFigi(String figi) {
        return figiToTickerMap.computeIfAbsent(figi, s -> instrumentsService.findByFigi(s).get(0).getTicker());
    }

    public static Map<String, Map<OperationType, List<Operation>>> getOperationsGroupedByFigiAndOperationStatusEquals(
            List<Operation> operations, OperationStatus operationStatus) {
        return operations.stream()
                .filter(operation -> operationStatus != null && operationStatus.equals(operation.status))
                .filter(operation -> !StringUtils.isEmpty(operation.figi))
                .filter(operation -> Currency.USD.equals(operation.currency))
                .collect(Collectors.groupingBy(
                        operation -> operation.figi,
                        Collectors.groupingBy(operation -> operation.operationType)));
    }

}
