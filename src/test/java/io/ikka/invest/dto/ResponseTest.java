package io.ikka.invest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.config.ObjectMapperConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        ObjectMapperConfig.class,
})
class ResponseTest {
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void getPayload() {
        String json = objectMapper.writeValueAsString(Response.builder()
                .status(StatusType.OK)
                .time(OffsetDateTime.of(
                        2020, 11, 30, 0, 0, 0, 3000000,
                        ZoneOffset.ofHours(3)))
                .trackingId("1234")
                .build());
        assertEquals("{\"trackingId\":\"1234\",\"time\":\"2020-11-30T00:00:00.003+03:00\",\"status\":\"Ok\"}", json);

        System.out.println(OffsetDateTime.of(
                2020, 11, 30, 0, 0, 1, 0,
                ZoneOffset.ofHours(3)).toEpochSecond());
    }
}
