package ru.iandreyshev.supermarketSimulator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Basket {
    public void add(SupermarketProduct product, Number amount) {
        Number currAmount = m_container.getOrDefault(product, 0);
        Number newAmount = 0;

        switch (product.getType()) {
            case COUNT:
                newAmount = currAmount.intValue() + amount.intValue();
                break;
            case MASS:
                newAmount = currAmount.floatValue() + amount.floatValue();
                break;
        }

        m_container.put(product, newAmount);
        m_sumCost += Util.calcCost(product.getType(), product.getCost(), amount);
    }

    public Number remove(SupermarketProduct product, Number amount) {
        if (!m_container.containsKey(product)) {
            return 0;
        }

        Number removedAmount = 0;
        Number newAmount = 0;
        Number currAmount = m_container.getOrDefault(product, 0);

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

        m_container.put(product, newAmount);
        m_sumCost -= Util.calcCost(product.getType(), product.getCost(), removedAmount);
        return removedAmount;
    }

    public long getSumCost() {
        return m_sumCost;
    }

    public int getSize() {
        return m_container.size();
    }

    public Set<Map.Entry<SupermarketProduct, Number>> getProductsInfo() {
        return m_container.entrySet();
    }

    public Set<SupermarketProduct> getProductsSet() {
        return m_container.keySet();
    }

    public boolean isContain(Product product) {
        return m_container.containsKey(product);
    }

    public void clear() {
        m_container = new HashMap<>();
        m_sumCost = 0L;
    }

    private HashMap<SupermarketProduct, Number> m_container = new HashMap<>();
    private long m_sumCost;
}
