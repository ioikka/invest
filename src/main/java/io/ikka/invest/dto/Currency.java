package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Currency {
    @JsonProperty("USD")
    USD,
    @JsonProperty("RUB")
    RUB,
    @JsonProperty("EUR")
    EUR,
    ;
}
