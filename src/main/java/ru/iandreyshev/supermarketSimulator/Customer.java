package ru.iandreyshev.supermarketSimulator;

import ru.iandreyshev.util.Rand;
import java.util.Set;

public class Customer {
    public Customer(String name) {
        m_money = Rand.getInt(MAX_MONEY_COUNT);

        int randType = Rand.getInt(CustomerType.values().length - 1);
        m_type = CustomerType.values()[randType];

        int randPayType = Rand.getInt(PaymentType.values().length - 1);
        m_payType = PaymentType.values()[randPayType];

        m_name = name;
    }

    public Basket selectProducts(Set<SupermarketProduct> products) {
        Basket result = null;
        return result;
    }

    public CustomerType getType() {
        return m_type;
    }

    public PaymentType getPayType() {
        return m_payType;
    }

    public int getMoney() {
        return m_money;
    }

    public String getName() {
        return m_name;
    }

    private static final int MAX_MONEY_COUNT = 1500;

    private String m_name;
    private int m_money;
    private CustomerType m_type;
    private PaymentType m_payType;
}
