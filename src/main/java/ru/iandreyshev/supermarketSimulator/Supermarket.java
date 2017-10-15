package ru.iandreyshev.supermarketSimulator;

import ru.iandreyshev.util.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Supermarket {
    public Supermarket(Date startDate) {
        reset();
        m_startDate = startDate;
    }

    public void addProduct(Product newProduct, Integer count) {
        if (newProduct.getType() != ProductType.Count) {
            throw new IllegalArgumentException(
                    "Trying to add wrong type of " +
                            "product in the count-products list");
        }

        m_products.add(newProduct);
        m_countProducts.put(newProduct, count);
    }

    public void addProduct(Product newProduct, Float mass) {
        if (newProduct.getType() != ProductType.Mass) {
            throw new IllegalArgumentException(
                    "Trying to add wrong type of " +
                            "product in the mass-products list");
        }

        m_products.add(newProduct);
        m_massProducts.put(newProduct, mass);
    }

    public void reset() {
        m_products = new HashSet<>();
        m_countProducts = new HashMap<>();
        m_massProducts = new HashMap<>();
    }

    public void startWork(long workTime) {
        Logger.Message(m_startDate, "Supermarket products have been formed:");
        for (Map.Entry<Product, Integer> record : m_countProducts.entrySet()) {
            Logger.Product(record.getKey(), record.getValue());
        }
        for (Map.Entry<Product, Float> record : m_massProducts.entrySet()) {
            Logger.Product(record.getKey(), record.getValue());
        }
        Logger.Message(m_startDate, "Supermarket is opened");
    }

    private Date m_startDate;
    private HashSet<Product> m_products;
    private HashMap<Product, Integer> m_countProducts;
    private HashMap<Product, Float> m_massProducts;
}
