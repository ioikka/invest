package io.ikka.invest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.ikka.invest.jackson.LocalDateFromEpochDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Yield {
    @JsonDeserialize(using = LocalDateFromEpochDeserializer.class)
    private LocalDate date;
    private BigDecimal value;
}
