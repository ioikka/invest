package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusType {
    @JsonProperty("done")
    DONE(1, "done", "done"),
    @JsonProperty("decline")
    DECLINE(2, "decline", "decline"),
    @JsonProperty("Ok")
    OK(2, "ok", "ok"),
    ;

    private final int id;
    private final String englishShortName;
    private final String englishDescription;
}
