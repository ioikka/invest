package io.ikka.invest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.config.ObjectMapperConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        ObjectMapperConfig.class,
})
class YieldTest {
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void test_date_deserializer() {
        Yield yield = objectMapper.readValue("{\"date\": 1606683601,\"value\": 508931.29}", Yield.class);

        assertEquals(
                Yield.builder()
                        .date(LocalDate.of(2020, 11, 30))
                        .value(BigDecimal.valueOf(508931.29))
                        .build(),
                yield
        );
    }
}
