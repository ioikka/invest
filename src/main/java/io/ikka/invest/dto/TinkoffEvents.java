package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TinkoffEvents {
    private Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Payload {
        private List<Operation> items;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Operation {
        private Long id;
        /**
         * example: 2020-11-27T14:11:44.06+03:00
         */
        private ZonedDateTime date;
        private OperationType operationType;
        @JsonProperty("isMarginCall")
        private boolean isMarginCall;
        private String issuer;
        private InstrumentType instrumentType;
        private String ticker;
        private String isin;
        private String showName;
        private BigDecimal payment;
        private BigDecimal commission;
        private BigDecimal commissionRub;
        private Currency commissionCurrency;
        private Currency currency;
        private StatusType status;
        private AccountType accountType;
        private Long quantity;
        private Long quantityRest;
        private String accountName;
        private String description;
        private List<Trade> trades;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Trade {
        private Long id;
        private ZonedDateTime date;
        private Long quantity;
        private BigDecimal price;

    }

}
