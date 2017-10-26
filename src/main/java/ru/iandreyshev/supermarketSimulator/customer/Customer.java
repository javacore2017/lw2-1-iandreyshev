package ru.iandreyshev.supermarketSimulator.customer;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.util.Util;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.util.Rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer implements IBuyer {
    public Customer(String name) {
        this.name = name;
        money = Rand.getInt(MAX_MONEY_COUNT);
        bonus = Rand.getInt(MAX_MONEY_COUNT);

        int randType = Rand.getInt(BuyerType.values().length - 1);
        type = BuyerType.values()[randType];

        int randPayType = Rand.getInt(PaymentType.values().length - 1);
        payType = PaymentType.values()[randPayType];
        resetBasket();
    }

    public Pair<SupermarketProduct, Number> takeRandProduct(Basket basketToTake) {
        if (basketToTake.getSize() == 0) {
            return null;
        }
        HashMap<SupermarketProduct, Number> basketContent = basketToTake.getProducts();
        List<SupermarketProduct> products = new ArrayList<>(basketContent.keySet());
        int maxIndex = products.size() - 1;
        int randIndex = Rand.getInt(maxIndex);
        SupermarketProduct newProduct = products.get(randIndex);
        Number amount = Util.toAmount(newProduct.getType(), Rand.getFloat(MAX_AMOUNT));

        if (amount.floatValue() == 0) {
            return null;
        }
        int cost = Util.multiplicate(newProduct.getType(), newProduct.getCost(), amount);

        if (getMoney() - cost < this.basket.getSumCost()) {
            return null;
        }
        Number takingAmount = basketToTake.remove(newProduct, amount);
        this.basket.add(newProduct, takingAmount);
        return new Pair<>(newProduct, takingAmount);
    }

    public void resetBasket() {
        basket = new Basket();
    }

    public BuyerType getType() {
        return type;
    }

    public PaymentType getPayType() {
        return payType;
    }

    public int getMoney() {
        return (payType == PaymentType.BONUS) ? bonus : money;
    }

    public void addBonus(int bonus) {
        bonus += bonus;
    }

    public String getName() {
        return name;
    }

    public Basket getBasket() {
        return basket;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }

    private static final int MAX_MONEY_COUNT = 5000;
    private static final float MAX_AMOUNT = 30;

    private boolean isArrived;
    private String name;
    private int money;
    private int bonus;
    private BuyerType type;
    private PaymentType payType;
    private Basket basket;
}
