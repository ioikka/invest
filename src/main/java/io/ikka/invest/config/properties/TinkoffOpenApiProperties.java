package io.ikka.invest.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Setter
@Getter
@Configuration
@ConfigurationProperties("io.ikka.ru.tinkoff")
public class TinkoffOpenApiProperties {
    private String token;


    @PostConstruct
    public void logSettings() {
        log.info("*********** TinkoffOpenApiProperties ***********");
        log.info("token = {}***********", token.substring(0, 2));
        log.info("************************************************");
    }
}
