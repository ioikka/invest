package io.ikka.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClosedPosition {
    private BigDecimal buyAmount;
    private BigDecimal sellAmount;
    private BigDecimal brokerFeeAmount;
    private String ticker;
}
