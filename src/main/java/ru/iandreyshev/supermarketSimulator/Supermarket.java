package ru.iandreyshev.supermarketSimulator;

import ru.iandreyshev.util.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Supermarket extends CashDesk {
    public Supermarket(Date startDate) {
        setStartDate(startDate);
        setRetireDiscount(RETIRE_DISCOUNT);
        reset();
    }

    public void setStartDate(Date startDate) {
        m_startDate = startDate;
    }

    public void addProduct(SupermarketProduct product) {
        m_products.add(product.getProduct(), product.getAmount());
        m_bonuses.put(product.getProduct(), product.getBonus());
    }

    public void startWork(int workTime) {
        Logger.Message(m_startDate, "Supermarket products have been formed:");

        for (Map.Entry<Product, Number> entry : m_products.entrySet()) {
            Logger.Product(entry.getKey(), entry.getValue());
        }

        Logger.Message(m_startDate, "Supermarket is opened");
    }

    public ProductContainer getProducts() {
        return m_products;
    }

    public Date getStartDate() {
        return m_startDate;
    }

    public void reset() {
        m_products = new ProductContainer();
        m_bonuses = new HashMap<>();
    }

    private Float RETIRE_DISCOUNT = 0.1f;

    private Date m_startDate;
    private ProductContainer m_products;
    private HashMap<Product, Integer> m_bonuses;
}
