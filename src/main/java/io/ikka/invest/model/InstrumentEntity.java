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
    public String isin;

    /**
     * Минимальный шаг цены.
     */
    @Nullable
    public BigDecimal minPriceIncrement;

    /**
     * Размер лота.
     */
    public int lot;

    /**
     * Валюта цены инструмента.
     */
    @Nullable
    public Currency currency;

    /**
     * Название компании-эмитента
     */
    @Column(nullable = false)
    @NotNull
    public String name;

    /**
     * Тип инструмента.
     */
    @Column(nullable = false)
    @NotNull
    public InstrumentType type;
}
