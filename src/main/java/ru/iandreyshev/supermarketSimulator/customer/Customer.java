package ru.iandreyshev.supermarketSimulator.customer;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.util.Util;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.util.Rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    public Customer(String name) {
        money = Rand.getInt(MAX_MONEY_COUNT);

        int randType = Rand.getInt(CustomerType.values().length - 1);
        type = CustomerType.values()[randType];

        int randPayType = Rand.getInt(PaymentType.values().length - 1);
        payType = PaymentType.values()[randPayType];

        this.name = name;
    }

    public Pair<SupermarketProduct, Number> takeRandProduct(Basket basket) {
        if (basket.getSize() == 0) {
            return null;
        }

        HashMap<SupermarketProduct, Number> basketContent = basket.getProducts();
        List<SupermarketProduct> products = new ArrayList<>(basketContent.keySet());

        int maxIndex = products.size() - 1;
        int randIndex = Rand.getInt(maxIndex);

        SupermarketProduct newProduct = products.get(randIndex);
        float takingAmount = Rand.getFloat(MAX_AMOUNT);
        Number amount = Util.toAmount(newProduct.getType(), takingAmount);

        if (amount.floatValue() == 0) {
            return null;
        }

        int cost = Util.multiplication(newProduct.getType(), newProduct.getCost(), amount);
        if (money - cost < this.basket.getSumCost()) {
            return null;
        }

        basket.remove(newProduct, amount);
        this.basket.add(newProduct, amount);

        return new Pair<>(newProduct, amount);
    }

    public CustomerType getType() {
        return type;
    }

    public PaymentType getPayType() {
        return payType;
    }

    public Integer getMoney() {
        return money;
    }

    public void addBonus(int bonus) {
        money += bonus;
    }

    public String getName() {
        return name;
    }

    public Basket getBasket() {
        return basket;
    }

    private static final int MAX_MONEY_COUNT = 5000;
    private static final float MAX_AMOUNT = 30;

    private String name;
    private int money;
    private CustomerType type;
    private PaymentType payType;
    private Basket basket = new Basket();
}
