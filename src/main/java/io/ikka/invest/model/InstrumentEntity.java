package io.ikka.invest.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.invest.openapi.models.Currency;
import ru.tinkoff.invest.openapi.models.market.InstrumentType;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "instruments",
        indexes = {
                @Index(name = "i_instruments_figi", columnList = "figi"),
                @Index(name = "i_instruments_isin", columnList = "isin"),
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class InstrumentEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Идентификатор инструмента.
     */
    @Column(nullable = false, unique = true)
    @NotNull
    private String figi;

    /**
     * Краткий биржевой идентификатор ("тикер").
     */
    @Column(nullable = false)
    @NotNull
    private String ticker;

    /**
     * Международный идентификационный код ценной бумаги.
     */
    @Nullable
    private String isin;

    /**
     * Минимальный шаг цены.
     */
    @Nullable
    private BigDecimal minPriceIncrement;

    /**
     * Размер лота.
     */
    private int lot;

    /**
     * Валюта цены инструмента.
     */
    @Nullable
    private Currency currency;

    /**
     * Название компании-эмитента
     */
    @Column(nullable = false)
    @NotNull
    private String name;

    /**
     * Тип инструмента.
     */
    @Column(nullable = false)
    @NotNull
    private InstrumentType type;
}
