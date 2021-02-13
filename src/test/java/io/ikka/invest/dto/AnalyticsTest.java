package io.ikka.invest.dto;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.config.ObjectMapperConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@SpringBootTest(classes = {
        ObjectMapperConfig.class,
})
class AnalyticsTest {
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void getCoupons() {
        String path = "mocks/006_stats_rub_6m.json";
        JavaType type = objectMapper.getTypeFactory().constructParametricType(Response.class, Analytics.class);
        Response<Analytics> analyticsResponse = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(path).getInputStream()))) {
            analyticsResponse = objectMapper.readValue(br.lines().collect(Collectors.joining("\n")), type);
        }

        Analytics payload = analyticsResponse.getPayload();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        payload.getYieldAbsoluteData().forEach(r ->
        {

//            System.out.println(r.getDate().format(yyyyMMdd));
            System.out.println(r.getDate().format(yyyyMMdd) + " " + r.getValue());
        });
    }
}
