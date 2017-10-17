package ru.iandreyshev.supermarketSimulator;

import ru.iandreyshev.util.Rand;

import java.util.Calendar;
import java.util.Random;

public class Customer {
    public Customer(String name) {
        m_money = Rand.getInt(MAX_MONEY_COUNT);
        int randomType = Rand.getInt(CustomerType.values().length - 1);
        m_type = CustomerType.values()[randomType];
    }

    public Basket selectProducts(ProductContainer products) {
        Basket result = null;
        return result;
    }

    public String getName() {
        return m_name;
    }

    private static final int MAX_MONEY_COUNT = 1500;

    private String m_name;
    private int m_money;
    private CustomerType m_type;
}
