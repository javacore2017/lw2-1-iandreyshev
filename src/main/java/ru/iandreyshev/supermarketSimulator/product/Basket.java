package ru.iandreyshev.supermarketSimulator.product;

import ru.iandreyshev.supermarketSimulator.util.Util;

import java.util.*;

public class Basket {
    public void add(SupermarketProduct product, Number amount) {
        if (amount == null || amount.floatValue() == 0) {
            return;
        }
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
        sumCost += Util.multiplicate(product.getType(), product.getCost(), amount);
    }

    public Number remove(SupermarketProduct product, Number amountToTake) {
        if (!container.containsKey(product)) {
            return null;
        }
        Number newAmount = null;
        Number currAmount = container.get(product);
        switch (product.getType()) {
            case COUNT:
                if (currAmount.intValue() > amountToTake.intValue()) {
                    newAmount = currAmount.intValue() - amountToTake.intValue();
                }
                break;
            case MASS:
                if (currAmount.floatValue() > amountToTake.floatValue()) {
                    newAmount = currAmount.floatValue() - amountToTake.floatValue();
                }
                break;
        }

        Number removedAmount;
        if (newAmount == null) {
            removedAmount = currAmount;
            container.remove(product);
        } else {
            removedAmount = amountToTake;
            container.put(product, newAmount);
        }

        sumCost -= Util.multiplicate(product.getType(), product.getCost(), removedAmount);
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
