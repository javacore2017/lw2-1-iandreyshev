package ru.iandreyshev.supermarketSimulator.customer;

import ru.iandreyshev.supermarketSimulator.product.Basket;

public interface IBuyer {
    BuyerType getType();

    PaymentType getPayType();

    int getMoney();

    void addBonus(int bonus);

    Basket getBasket();
}
