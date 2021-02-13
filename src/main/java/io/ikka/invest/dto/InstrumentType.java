package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InstrumentType {
    @JsonProperty("Stock")
    STOCK(1, "Stock", "Stock"),
    @JsonProperty("FX")
    FX(2, "FX", "FX"),
    @JsonProperty("Bond")
    BOND(3, "Bond", "Bond"),
    @JsonProperty("Currency")
    CURRENCY(4, "Currency", "Currency"),
    @JsonProperty("ETF")
    ETF(5, "ETF", "ETF"),
    @JsonProperty("Future")
    FUTURE(6, "Future", "Future"),
    ;
    private final int id;
    private final String englishShortName;
    private final String englishDescription;
}
