package ru.iandreyshev.supermarketSimulator;

public class Product {
    public Product(ProductType type, String name, int cost) {
        m_type = type;
        m_name = name;
        m_cost = cost;
    }

    public ProductType getType() {
        return m_type;
    }

    public String getName() {
        return m_name;
    }

    public Integer getCost() {
        return m_cost;
    }

    private ProductType m_type;
    private String m_name;
    private int m_cost;
}
