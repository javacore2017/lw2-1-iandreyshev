package ru.iandreyshev.supermarketSimulator;

import java.util.HashMap;
import java.lang.Exception;

public class Busket {
    public Busket() {
        m_container = new ProductContainer();
    }

    public Long getSumCost() {
        return m_sumCost;
    }

    public int getSize() {
        return m_container.getSize();
    }

    public void add(Product product, int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost can not be less than zero");
        } else if (Long.MAX_VALUE - cost < m_sumCost) {
            throw new RuntimeException("Basket add to cost overflow");
        }

        m_sumCost += cost;
    }

    public void remove(Product product, int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost can not be less than zero");
        } else if (cost > m_sumCost) {
            throw new RuntimeException("Basket erase from cost overflow");
        }

        m_sumCost -= cost;
    }

    public void clear() {
        m_container = new ProductContainer();
        m_sumCost = 0L;
    }

    private Long m_sumCost = 0L;
    private ProductContainer m_container;
}
