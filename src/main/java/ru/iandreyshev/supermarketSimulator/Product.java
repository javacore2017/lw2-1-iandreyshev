package ru.iandreyshev.supermarketSimulator;

public class Product {
    public Product(ProductType type, String name, boolean isAdultOnly) {
        m_type = type;
        m_name = name;
        m_isAdultOnly = isAdultOnly;
    }

    public Product(Product instance) {
        m_type = instance.m_type;
        m_name = instance.m_name;
        m_isAdultOnly = instance.m_isAdultOnly;
    }

    public ProductType getType() {
        return m_type;
    }

    public String getName() {
        return m_name;
    }

    public Boolean isAdultOnly() {
        return m_isAdultOnly;
    }

    private ProductType m_type;
    private String m_name;
    private boolean m_isAdultOnly;
}
