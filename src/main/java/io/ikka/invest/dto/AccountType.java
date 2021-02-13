package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountType {
    @JsonProperty("Tinkoff")
    TINKOFF(1, "Tinkoff", "Tinkoff"),
    ;

    private final int id;
    private final String englishShortName;
    private final String englishDescription;
}
