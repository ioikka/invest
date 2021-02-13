package io.ikka.invest.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ikka.invest.service.MarketService;
import io.ikka.invest.service.OperationsService;
import io.ikka.invest.service.OrdersService;
import io.ikka.invest.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.invest.openapi.models.operations.OperationsList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
//@Component
public class MyCLR1 implements CommandLineRunner {
    @Value("${api.url}")
    private String url;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final PortfolioService portfolioService;
    private final OperationsService operationsService;
    private final MarketService marketService;
    private final OrdersService ordersService;

    @Override
    public void run(String... args) throws Exception {
//        ResponseEntity<RestResponse<Portfolio>> forEntity = restTemplate.getForEntity(url + "portfolio", RestResponse.class);
//        ParameterizedTypeReference<RestResponse<Portfolio>> responseType = new ParameterizedTypeReference<>() {
//        };
//        var portfolioResponse =
//                restTemplate.exchange(url + "portfolio", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponse<Portfolio>>() {
//                });
//        log.info("{}", objectMapper.writeValueAsString(portfolioResponse.getBody()));
//
//        var response =
//                restTemplate.exchange(url + "portfolio/currencies", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponse<PortfolioCurrencies>>() {
//                });

//        log.info("{}", objectMapper.writeValueAsString(response.getBody()));


//        List<Order> orders = ordersService.getOrders();
//        log.info("{}", orders);

//        InstrumentsList marketStocks = marketService.getMarketStocks();
//        log.info("{}", marketStocks.instruments.size());
//        log.info("{}", marketStocks);
//        log.info("{}", marketStocks.total);
        OperationsList operations = operationsService.getOperations();
        log.info("{}", operations);

//        log.info("{}", portfolioService.getPortfolio());
//        log.info("{}", portfolioService.getPortfolioCurrencies());
        String operationsListJson = objectMapper.writeValueAsString(operations);
        Files.writeString(Path.of("operations_"
                + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .replaceAll(":", "_")
                .replaceAll("-", "_")
                .replaceAll("\\.", "_")
                .replaceAll("T", "_")
                + "json"), operationsListJson, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);


    }
}
