package ru.iandreyshev.supermarketSimulator.customer;

import ru.iandreyshev.supermarketSimulator.product.Basket;
import java.math.BigDecimal;

public interface IBuyer {
    BuyerType getType();

    PaymentType getPayType();

    BigDecimal getMoney();

    void addBonus(BigDecimal bonus);

    Basket getBasket();
}
