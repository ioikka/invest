package io.ikka.invest.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("io.ikka.ru.tinkoff")
public class TinkoffOpenApiProperties {
    private String token;
}
