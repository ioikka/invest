package io.ikka.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnaliticsRequest {
    private BrokerAccountType brokerAccountType;
    private Currency currency;
    private PeriodType period;
}
