package ru.iandreyshev.supermarketSimulator;

import java.util.*;

public class Supermarket {
    public Supermarket() {
        m_products = new Basket();
        createCashDesk();
    }

    public void addProduct(SupermarketProduct product, Number amount) {
        m_products.add(product, amount);
        m_cashDesk.addProduct(product);
    }

    public boolean isEmpty() {
        return m_products.getSize() == 0;
    }

    public Set<Map.Entry<SupermarketProduct, Number>> getProductsInfo() {
        return m_products.getProductsInfo();
    }

    public Set<SupermarketProduct> getProductsSet() {
        return m_products.getProductsSet();
    }

    private Float RETIRE_DISCOUNT_SIZE = 0.05f;

    private Basket m_products;
    private CashDesk m_cashDesk;

    private void createCashDesk() {
        m_cashDesk = new CashDesk();
        m_cashDesk.addDiscount(DiscountType.RETIRE, RETIRE_DISCOUNT_SIZE);
    }
}
