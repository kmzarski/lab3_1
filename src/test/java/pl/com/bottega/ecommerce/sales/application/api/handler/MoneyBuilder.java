package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.apache.commons.lang3.RandomStringUtils;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;
import java.util.Random;

public class MoneyBuilder {
    private Money money;

    public static MoneyBuilder defaultValures() {
        return new MoneyBuilder();
    }

    public static MoneyBuilder clone(Money clonnedMoney) {
        MoneyBuilder builder = defaultValures();
        builder.setDenomination(clonnedMoney.getDenomination());
        builder.setCurrencyCode(clonnedMoney.getCurrencyCode());
        return builder;
    }

    public static MoneyBuilder random() {
        MoneyBuilder builder = defaultValures();
        builder.setCurrencyCode(RandomStringUtils.randomAlphabetic(10));
        builder.setDenomination(BigDecimal.valueOf(new Random().nextLong()));
        return builder;
    }

    public MoneyBuilder setDenomination(BigDecimal denomination) {
        money.setDenomination(denomination);
        return this;
    }

    public MoneyBuilder setCurrencyCode(String currencyCode) {
        money.setCurrencyCode(currencyCode);
        return this;
    }

    public Money build() {
        return money;
    }
}
