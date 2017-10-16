package ru.iandreyshev.supermarketSimulator;

import ru.iandreyshev.util.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Supermarket {
    public Supermarket(Date startDate) {
        setStartDate(startDate);
        reset();
    }

    public void setStartDate(Date startDate) {
        m_startDate = startDate;
    }

    public void addProduct(Product newProduct, Number amount) {
        m_products.add(newProduct, amount);
    }

    public void reset() {
        m_products = new ProductContainer();
    }

    public void startWork(long workTime) {
        Logger.Message(m_startDate, "Supermarket products have been formed:");

        for (Map.Entry<Product, Number> record : m_products.getEntrySet()) {
            Logger.Product(record.getKey(), record.getValue());
        }

        Logger.Message(m_startDate, "Supermarket is opened");
    }

    private Date m_startDate;
    private ProductContainer m_products;
}
