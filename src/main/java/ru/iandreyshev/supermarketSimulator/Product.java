package ru.iandreyshev.supermarketSimulator;

public class Product {
    public Product(ProductType type, String name, int cost, boolean isAdultOnly) {
        m_type = type;
        m_name = name;
        m_cost = cost;
        m_isAdultOnly = isAdultOnly;
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

    public Boolean isAdultOnly() {
        return m_isAdultOnly;
    }

    private ProductType m_type;
    private String m_name;
    private int m_cost;
    private Boolean m_isAdultOnly;
}
