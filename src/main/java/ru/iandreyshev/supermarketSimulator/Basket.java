package ru.iandreyshev.supermarketSimulator;

import java.util.HashMap;
import java.lang.Exception;

public class Basket {
    public Basket() {
        m_container = new ProductContainer();
    }

    public Long getSumCost() {
        return m_sumCost;
    }

    public int getSize() {
        return m_container.getSize();
    }

    public void add(Product product, Number amount) {
        m_sumCost += Util.calcCost(product, amount);
    }

    public void remove(Product product, Number amount) {
        m_sumCost -= Util.calcCost(product, amount);
    }

    public void clear() {
        m_container = new ProductContainer();
        m_sumCost = 0L;
    }

    private Long m_sumCost = 0L;
    private ProductContainer m_container;
}
