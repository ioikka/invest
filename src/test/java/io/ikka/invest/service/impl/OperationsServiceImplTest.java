package io.ikka.invest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.config.ObjectMapperConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.invest.openapi.models.operations.Operation;
import ru.tinkoff.invest.openapi.models.operations.OperationStatus;
import ru.tinkoff.invest.openapi.models.operations.OperationType;
import ru.tinkoff.invest.openapi.models.operations.OperationsList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = {
        ObjectMapperConfig.class,
})
class OperationsServiceImplTest {
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void getOperations() {
        String resourceFileAsString = getResourceFileAsString("mocks/operations_2020_12_23_20_56_37_5126275.json");
        System.out.println(resourceFileAsString);
        OperationsList operationsList = objectMapper.readValue(resourceFileAsString, OperationsList.class);

        List<Operation> buys = operationsList.operations.stream()
                .filter(r -> "BBG000BP0HX7".equals(r.figi))
                .filter(r -> OperationStatus.Done.equals(r.status))
                .filter(r -> OperationType.Buy.equals(r.operationType))
                .collect(Collectors.toList());


        List<Operation> sells = operationsList.operations.stream()
                .filter(r -> "BBG000BP0HX7".equals(r.figi))
                .filter(r -> OperationStatus.Done.equals(r.status))
                .filter(r -> OperationType.Sell.equals(r.operationType))
                .collect(Collectors.toList());

        List<Operation> brokerCommissions = operationsList.operations.stream()
                .filter(r -> "BBG000BP0HX7".equals(r.figi))
                .filter(r -> OperationStatus.Done.equals(r.status))
                .filter(r -> OperationType.BrokerCommission.equals(r.operationType))
                .collect(Collectors.toList());

        BigDecimal buysSum = buys.stream().map(r -> r.payment).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sellsSum = sells.stream().map(r -> r.payment).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal commissionsSum = brokerCommissions.stream().map(r -> r.payment).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(buysSum);
        System.out.println(sellsSum);
        System.out.println(commissionsSum);

        System.out.println(sellsSum.add(buysSum).add(commissionsSum));
    }

    public static String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = OperationsServiceImpl.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
