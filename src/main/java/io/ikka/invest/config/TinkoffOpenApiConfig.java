package io.ikka.invest.config;

import io.ikka.invest.config.properties.TinkoffOpenApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.*;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApiFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TinkoffOpenApiConfig {
    private final TinkoffOpenApiProperties tinkoffOpenApiProperties;

    @Bean
    public OkHttpOpenApiFactory okHttpOpenApiFactory() {
        return new OkHttpOpenApiFactory(tinkoffOpenApiProperties.getToken(), Logger.getLogger(TinkoffOpenApiConfig.class.getSimpleName()));

    }

    @Bean
    public OpenApi openApiClient(OkHttpOpenApiFactory okHttpOpenApiFactory) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        return okHttpOpenApiFactory.createOpenApiClient(executorService);
    }

    @Bean
    public PortfolioContext portfolioContext(OpenApi openApiClient) {
        return openApiClient.getPortfolioContext();
    }


    @Bean
    public OperationsContext operationsContext(OpenApi openApiClient) {
        return openApiClient.getOperationsContext();
    }

    @Bean
    public MarketContext marketContext(OpenApi openApiClient) {
        return openApiClient.getMarketContext();
    }

    @Bean
    public UserContext userContext(OpenApi openApiClient) {
        return openApiClient.getUserContext();
    }

    @Bean
    public OrdersContext ordersContext(OpenApi openApiClient) {
        OrdersContext ordersContext = openApiClient.getOrdersContext();
        return ordersContext;
    }


    @Bean
    public StreamingContext streamingContext(OpenApi openApiClient) {
        return openApiClient.getStreamingContext();
    }
}
