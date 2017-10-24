package ru.iandreyshev.supermarketSimulator.product;

import ru.iandreyshev.supermarketSimulator.util.Util;

import java.util.*;

public class Basket {
    public void add(SupermarketProduct product, Number amount) {
        Number currAmount = container.getOrDefault(product, 0);
        Number newAmount = 0;

        switch (product.getType()) {
            case COUNT:
                newAmount = currAmount.intValue() + amount.intValue();
                break;
            case MASS:
                newAmount = currAmount.floatValue() + amount.floatValue();
                break;
        }

        container.put(product, newAmount);
        sumCost += Util.multiplication(product.getType(), product.getCost(), amount);
    }

    public Number remove(SupermarketProduct product, Number amount) {
        if (!container.containsKey(product)) {
            return 0;
        }

        Number removedAmount = 0;
        Number newAmount = 0;
        Number currAmount = container.getOrDefault(product, 0);

        switch (product.getType()) {
            case COUNT:
                if (currAmount.intValue() > amount.intValue()) {
                    newAmount = currAmount.intValue() - amount.intValue();
                    removedAmount = currAmount.intValue() - newAmount.intValue();
                }
                break;
            case MASS:
                if (currAmount.floatValue() > amount.floatValue()) {
                    newAmount = currAmount.floatValue() - amount.floatValue();
                    removedAmount = currAmount.floatValue() - newAmount.floatValue();
                }
                break;
        }

        container.put(product, newAmount);
        sumCost -= Util.multiplication(product.getType(), product.getCost(), removedAmount);
        return removedAmount;
    }

    public long getSumCost() {
        return sumCost;
    }

    public int getSize() {
        return container.size();
    }

    public HashMap<SupermarketProduct, Number> getProducts() {
        return container;
    }

    public Set<Map.Entry<SupermarketProduct, Number>> entrySet() {
        return getProducts().entrySet();
    }

    public void clear() {
        container = new HashMap<>();
        sumCost = 0L;
    }

    private HashMap<SupermarketProduct, Number> container = new HashMap<>();
    private long sumCost;
}
