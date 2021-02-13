package io.ikka.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analytics {
    private Long coupons;
    private BigDecimal cashOutcome;
    private BigDecimal cashIncome;
    private BigDecimal turnover;
    private BigDecimal dividends;
    private BigDecimal yield;
    private BigDecimal yieldRelative;
    private List<Yield> yieldAbsoluteData;
}
