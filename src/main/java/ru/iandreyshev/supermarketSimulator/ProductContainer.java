package ru.iandreyshev.supermarketSimulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductContainer {
    public ProductContainer() {
        m_container = new HashMap<>();
    }

    public void add(Product product, Number amount) {
        Number currAmount = m_container.getOrDefault(product, 0);
        Number newAmount = 0;

        switch (product.getType()) {
            case Count:
                newAmount = currAmount.intValue() + amount.intValue();
                break;
            case Mass:
                newAmount = currAmount.floatValue() + amount.floatValue();
                break;
        }

        m_container.put(product, newAmount);
    }

    public Number remove(Product product, Number amountToRemove) {
        Number currAmount = m_container.getOrDefault(product, 0);
        Number removedAmount = amountToRemove;

        switch (product.getType()) {
            case Count:
                if (currAmount.intValue() > amountToRemove.intValue()) {
                    removedAmount = currAmount;
                    m_container.remove(product);
                    break;
                }
                m_container.put(product, currAmount.intValue() - amountToRemove.intValue());
            case Mass:
                if (currAmount.floatValue() <= amountToRemove.floatValue()) {
                    removedAmount = currAmount;
                    m_container.remove(product);
                    break;
                }
                m_container.put(product, currAmount.floatValue() - amountToRemove.floatValue());
        }

        return removedAmount;
    }

    public int getSize() {
        return m_container.size();
    }

    public Set<Map.Entry<Product, Number>> getEntrySet() {
        return m_container.entrySet();
    }

    private HashMap<Product, Number> m_container;
}
