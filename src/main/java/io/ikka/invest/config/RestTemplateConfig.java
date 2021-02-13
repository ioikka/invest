package io.ikka.invest.config;

import io.ikka.invest.config.properties.TinkoffOpenApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig {
    private final TinkoffOpenApiProperties tinkoffOpenApiProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("some uri")
                .additionalInterceptors((request, body, execution) -> {
                    request
                            .getHeaders()
                            .add(
                                    "Authorization",
                                    "Bearer " + tinkoffOpenApiProperties.getToken());
                    return execution.execute(request, body);
                }).build();
    }
}
