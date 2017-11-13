package ru.iandreyshev.supermarketSimulator.customer;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.util.Util;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.util.Rand;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer implements IBuyer {
    private static final int MAX_MONEY_COUNT = 5000;
    private static final float MAX_AMOUNT = 30;

    private boolean isArrived;
    private String name;
    private BigDecimal money;
    private BigDecimal bonus;
    private BuyerType type;
    private PaymentType payType;
    private Basket basket;

    public Customer(String name) {
        this.name = name;
        money = new BigDecimal(Rand.randInt(MAX_MONEY_COUNT));
        bonus = new BigDecimal(Rand.randInt(MAX_MONEY_COUNT));

        int randType = Rand.randInt(BuyerType.values().length - 1);
        type = BuyerType.values()[randType];

        int randPayType = Rand.randInt(PaymentType.values().length - 1);
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
        int randIndex = Rand.randInt(maxIndex);
        SupermarketProduct newProduct = products.get(randIndex);
        Number amount = Util.toAmount(newProduct.getType(), Rand.randFloat(MAX_AMOUNT));

        if (amount.floatValue() == 0) {
            return null;
        }
        BigDecimal cost = Util.multiplicate(newProduct.getType(), newProduct.getCost(), amount);
        BigDecimal resultMoney = getMoney().subtract(cost);

        if (resultMoney.compareTo(this.basket.getSumCost()) < 0) {
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

    public BigDecimal getMoney() {
        return (payType == PaymentType.BONUS) ? bonus : money;
    }

    public void addBonus(BigDecimal bonus) {
        this.bonus = this.bonus.add(bonus);
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
}
