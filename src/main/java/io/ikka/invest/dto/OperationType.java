package io.ikka.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OperationType {
    @JsonProperty("BrokCom")
    BROKER_FEE(1, "BrokCom", "Broker fee", null, null),
    @JsonProperty("Buy")
    BUY(2, "Buy", "Buy", null, null),
    @JsonProperty("Sell")
    SELL(3, "Sell", "Sell", null, "Продажа ценной бумаги"),
    @JsonProperty("PayIn")
    MONEY_DEPOSIT(4, "PayIn", "PayIn", "", "Пополнение брокерского счета"),
    @JsonProperty("PayOut")
    MONEY_WITHDRAWAL(5, "PayOut", "PayOut", "", "Вывод с брокерского счета"),
    @JsonProperty("Dividend")
    DIVIDEND_PAYOUT(6, "Dividend", "Dividend", "", "Выплата дивидендов"),
    @JsonProperty("RegCom")
    SERVICE_FEE(7, "RegCom", "RegCom", "", "Списание комиссии за обслуживание"),
    @JsonProperty("BuyWithCard")
    BUY_WITH_CARD(8, "BuyWithCard", "BuyWithCard", "", "Покупка с карты"),
    @JsonProperty("TaxDvd")
    NDFL_DIVIDENTS_TAX(9, "TaxDvd", "TaxDvd", "", "Удержание НДФЛ по дивидендам"),
    @JsonProperty("Benefit")
    SECURITIES_INCOME(10, "Benefit", "Benefit", "", "Доход от ценной бумаги"),
    @JsonProperty("ExchCom")
    EXCHANGE_FEE(11, "ExchCom", "ExchCom", "", "Списание биржевой комиссии"),
    @JsonProperty("Tax")
    NDFL_TAX(12, "Tax", "Tax", "", "Удержание НДФЛ"),
    @JsonProperty("WritingOffVarMargin")
    WRITING_OFF_VAR_MARGIN(13, "WritingOffVarMargin", "WritingOffVarMargin", "", "Списание вариационной маржи"),
    @JsonProperty("AccruingVarMargin")
    ACCRUING_VAR_MARGIN(13, "AccruingVarMargin", "AccruingVarMargin", "", "Начисление вариационной маржи"),
    @JsonProperty("AutoConv")
    AUTO_CONV(14, "AutoConv", "AutoConv", "", "Автоконвертация при списании комиссии за сделку"),
    @JsonProperty("Repayment")
    REPAYMENT(15, "Repayment", "Repayment", "", "Погашение облигации"),
    @JsonProperty("TaxBack")
    TAX_BACK(16, "TaxBack", "TaxBack", "", "Корректировка налога"),
    @JsonProperty("MarginCom")
    MARGIN_FEE(17, "MarginCom", "MarginCom", "", "Списание комиссии за непокрытую позицию"),
    @JsonProperty("Coupon")
    COUPON(18, "Coupon", "Coupon", "", "Выплата купонов"),
    @JsonProperty("TaxCpn")
    TAX_COUPON(19, "TaxCpn", "TaxCpn", "", "Удержание НДФЛ по купонам"),
    ;

    private final int id;
    private final String englishShortName;
    private final String englishDescription;
    private final String russianShortName;
    private final String russianDescription;
}
