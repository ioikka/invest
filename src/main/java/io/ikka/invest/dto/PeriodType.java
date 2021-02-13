package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PeriodType {
    @JsonProperty("M")
    MONTH(1, "Month", "Month"),
    @JsonProperty("Y")
    YEAR(2, "Year", "Year"),
    @JsonProperty("6M")
    HALF_YEAR(3, "Six months", "Six months"),
    @JsonProperty("All")
    ALL(3, "All data", "All data"),
    ;
    private final int id;
    private final String englishShortName;
    private final String englishDescription;
}
